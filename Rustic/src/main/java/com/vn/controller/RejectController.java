package com.vn.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.common.ThymeleafUtil;
import com.vn.config.GoogleMailSender;
import com.vn.jpa.*;
import com.vn.model.RejectModel;
import com.vn.service.BillService;
import com.vn.service.ProductService;
import com.vn.service.Product_BillService;
import com.vn.service.RejectService;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reject/")
public class RejectController {

	@Resource
	private Product_BillService productBillService;

	@Resource
	private BillService billService;

	@Resource
	private RejectService rejectService;

	@Resource
	private ProductService productService;

	@ModelAttribute("report")
	public Report report(Model model) {
		model.addAttribute("mapError", new HashedMap<String, String>());
		return new Report();
	}

	@RequestMapping(value = "{id}/home.html", method = RequestMethod.GET)
	public String reject(@PathVariable("id") Long id, Model model, HttpSession session) {
		AuthUser authUser = (AuthUser) session.getAttribute("userLogin");
		if (authUser != null) {
			Bill checkAuth = billService.findOne(id);
			if (checkAuth.getAuthUser().getId() != authUser.getId()) {
				return "redirect:/";
			}
			List<Product_Bill> lsProductBill = productBillService.findByBill_Id(id);
			Bill bill = billService.findOne(id);
			if (bill != null) {
				model.addAttribute("bill", bill);
			}
			model.addAttribute("reject", lsProductBill);
			return "home/reject";
		} else {
			return "redirect:/";
		}
	}

	@RequestMapping(value = "home.html", method = RequestMethod.POST)
	public @ResponseBody String rejectPOST(@RequestBody(required = false) RejectModel rejectModel) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Map<String, String> map = new HashMap<>();
		try {
//            List<Product_Bill> lsProductBill = productBillService.findByBill_Id(rejectModel.getId());
			Reject reject = new Reject();
			reject.setCode(rejectModel.getCode());
			reject.setAddress(rejectModel.getAddress());
			reject.setEmail(rejectModel.getEmail());
			reject.setMobile(rejectModel.getMobile());
			reject.setName(rejectModel.getName());
			reject.setReason(rejectModel.getReason());
			rejectService.insert(reject);
			// insert reject
//            for (Product_Bill each : lsProductBill) {
//                Product product = productService.findOne(each.getProduct().getId());
//                if(product != null){
//                    product.setQuantity(product.getQuantity() + each.getQuantity());
//                    productService.update(product);
//                    // update số lượng product
//                }
//            }
			Bill bill = billService.findOne(rejectModel.getId());
			bill.setTypeStatus(Bill.STATUSPAYMENT.REJECT.value());
			billService.update(bill);
			map.put("success", "Đã gửi yêu cầu hủy đơn, vui lòng chờ cửa hàng xác nhận!");
			// update trạng thái hủy đơn
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(map);
	}

	@RequestMapping(value = "{id}/{code}/accept.html", method = RequestMethod.POST)
	public String rejectAccept(@PathVariable("code") String code, @PathVariable("id") long id) {
		try {
			Bill bill = billService.findByCode(code);
			List<Product_Bill> lsProductBill = productBillService.findByBill_Id(bill.getId());
			// insert reject
			for (Product_Bill each : lsProductBill) {
				Product product = productService.findOne(each.getProduct().getId());
				if (product != null) {
					product.setQuantity(product.getQuantity() + each.getQuantity());
					productService.update(product);
					// update số lượng product
				}
			}
			Reject reject = rejectService.findOne(id);
			reject.setStatus(1);
			rejectService.update(reject);
			bill.setTypeStatus(Bill.STATUSPAYMENT.UNPAID.value());
			billService.update(bill);
			Map<String, Object> responseMapMail = new HashMap<>();
			responseMapMail.put("bill", bill);
			new Thread(() -> {
				try {
					GoogleMailSender mailSender = new GoogleMailSender();
					final String htmlContent = ThymeleafUtil.getHtmlContentInClassPath(
							"html/MailRejectSuccess.html", (HashMap<String, Object>) responseMapMail);
					mailSender.sendSimpleMailWarningTLS("Rustic<trilmph05520@fpt.edu.vn>", bill.getEmail(),
							"[Rustic] Email yêu cầu hủy đơn của Quý Khách", htmlContent);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
			// update trạng thái hủy đơn
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/bill/listCancel.html";
	}

	@RequestMapping(value = "{id}/{code}/cancel.html", method = RequestMethod.POST)
	public String cancel(@PathVariable("code") String code, @PathVariable("id") long id) {
		try {
			Bill bill = billService.findOne(id);
			bill.setTypeStatus(Bill.STATUSPAYMENT.ORDER.value());
			billService.update(bill);
			// update trạng thái hủy đơn
			Reject reject = rejectService.findOne(id);
			reject.setStatus(2);
			rejectService.update(reject);
			Map<String, Object> responseMapMail = new HashMap<>();
			responseMapMail.put("bill", bill);
			new Thread(() -> {
				try {
					GoogleMailSender mailSender = new GoogleMailSender();
					final String htmlContent = ThymeleafUtil.getHtmlContentInClassPath(
							"html/MailRejectFail.html", (HashMap<String, Object>) responseMapMail);
					mailSender.sendSimpleMailWarningTLS("Rustic<trilmph05520@fpt.edu.vn>", bill.getEmail(),
							"[Rustic] Email yêu cầu hủy đơn của Quý Khách", htmlContent);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/bill/listCancel.html";
	}
}
