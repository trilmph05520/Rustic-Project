package com.vn.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vn.jpa.AuthUser;
import com.vn.jpa.Infomation;
import com.vn.model.InfomationModel;
import com.vn.service.AuthUserService;
import com.vn.service.BankInfoService;
import com.vn.service.InfomationService;
import com.vn.validation.service.InfomationFormValidator;

@Controller
@RequestMapping("/profile/")
public class ProfileController {

	@Resource
	private InfomationService infomationService;

	@Resource
	private AuthUserService authSer;

	@Resource
	private BankInfoService bankService;
	
	@Resource
    private InfomationFormValidator infoFormValidator;

	@GetMapping("detail.html")
	private ModelAndView profileDetail(Model model, HttpSession session) {
		try {

			AuthUser authUser = null;
			Infomation inf = null;
			InfomationModel infModel = new InfomationModel();
			if (session.getAttribute("account") != null) {
				authUser = authSer.findByUserName(session.getAttribute("account").toString());
				inf = infomationService.findByAuthUserId(authUser.getId());
				if (inf == null) {
					inf = new Infomation();
				}

				infModel.setFirstName(authUser.getFirstName());
				infModel.setLastName(authUser.getLastName());
				infModel.setProvince(inf.getProvince());
				infModel.setTown(inf.getTown());
				infModel.setGender(authUser.getGender());
				infModel.setPhone(inf.getPhone());
				infModel.setBank(inf.getBank());
				infModel.setAtmNumberBank(inf.getAtmNumber());
				infModel.setAddress(inf.getAddress());
				infModel.setEmailUser(authUser.getEmail());
			}
			model.addAttribute("profile", infModel);
			model.addAttribute("bankInfo", bankService.findAll());
			model.addAttribute("mapError", new HashMap<String, String>());
			ModelAndView modelAndView = new ModelAndView("admin/profile");
			return modelAndView;
		} catch (Exception e) {
			model.addAttribute("profile", new InfomationModel());
			model.addAttribute("bankInfo", bankService.findAll());
			model.addAttribute("mapError", new HashMap<String, String>());
			ModelAndView modelAndView = new ModelAndView("admin/profile");
			return modelAndView;
		}
	}

	@RequestMapping(value = "detail.html", method = RequestMethod.POST)
	public String profileSave(HttpSession session, Model model,
			@ModelAttribute("profile") @Valid InfomationModel infomationModel, BindingResult result,
			Pageable pageable) {
		Map<String, String> mapError = new HashedMap<String, String>();
		AuthUser authUser = authSer.findByUserName(session.getAttribute("account").toString());
		try {
			infoFormValidator.validateReportForm(infomationModel, result);
			if (result.hasErrors()) {
				for (Object obj : result.getAllErrors()) {
					if (obj instanceof ObjectError) {
						mapError.put(((ObjectError) obj).getCode(), ((ObjectError) obj).getDefaultMessage());
					}
				}
				model.addAttribute("mapError", mapError);
				model.addAttribute("bankInfo", bankService.findAll());
				return "admin/profile";
			} else {
				Infomation infomation = null;
				infomation = infomationService.findByAuthUserId(authUser.getId());
				if (infomation == null) {
					infomation = new Infomation();
				}
				authUser.setEmail(infomationModel.getEmailUser());
				authUser.setFirstName(infomationModel.getFirstName());
				authUser.setLastName(infomationModel.getLastName());
				authUser.setGender(infomationModel.getGender());
				authSer.update(authUser);
				infomation.setProvince(infomationModel.getProvince());
				infomation.setTown(infomationModel.getTown());
				infomation.setBank(infomationModel.getBank());
				infomation.setAtmNumber(infomationModel.getAtmNumberBank());
				infomation.setAddress(infomationModel.getAddress());
				infomation.setPhone(infomationModel.getPhone());
				infomation.setAuthUser(authUser);
				infomation.setIsDelete("N");
				if (infomation.getId() == null) {
					infomationService.create(infomation);
				} else {
					infomationService.update(infomation);
				}
			}
		} catch (Exception e) {
			model.addAttribute("mapError", mapError);
			model.addAttribute("bankInfo", bankService.findAll());
			mapError.put("errorProfile", "Lỗi không xác định");
			model.addAttribute("mapError", mapError);
			return "admin/profile";
		}
		return "redirect://profile/detail.html";
	}

}
