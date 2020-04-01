package com.vn.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.model.ChartDashboardBillOrder;
import com.vn.model.KeyValueStringIntegerModel;
import com.vn.service.BillService;
import com.vn.service.CategoryService;
import com.vn.service.Product_BillService;
import com.vn.service.RejectService;

@Controller
@RequestMapping("/admin/")
public class AdminController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private BillService billService;

    @Resource
    private Product_BillService productBillService;

    @Resource
    private RejectService rejectService;

    private static Date date = new Date();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    @RequestMapping(value = "login.html", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        return "admin/login";
    }

    //    @PreAuthorize("hasAnyAuthority('Administrators')")
    @GetMapping(value = {"dashboard.html"})
    public String index(ModelMap modelMap, HttpSession session) {
        try {
//            ngày đó bán được số lượng bao nhiêu từng sản phẩm
            List<KeyValueStringIntegerModel> ls = productBillService.listCountBillOrGrDateNow();
            modelMap.addAttribute("lsOrderDateNow", ls);

            DateTime time = new DateTime();
            List<KeyValueStringIntegerModel> lsLabel = new ArrayList<>();
            date = time.plusDays(-6).toDate();
            for (int i = 0; i < 7; i++) {
                String label = sdf.format(date);
                KeyValueStringIntegerModel model = new KeyValueStringIntegerModel(label, BigDecimal.ZERO);
                lsLabel.add(model);
                date = time.plusDays(-5 + i).toDate();
            }
            List<ChartDashboardBillOrder> response = billService.listSumTotalForDashboard(date);
            for (KeyValueStringIntegerModel each : lsLabel) {
                for (ChartDashboardBillOrder res : response) {
                    if (each.getKey().compareTo(res.getDate()) == 0) {
                        each.setValue(res.getTotal());
                    }
                }
            }
            modelMap.addAttribute("lsBill", lsLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "admin/dashboard";
    }

    @RequestMapping(value = "chartBill", method = RequestMethod.GET)
    public @ResponseBody
    String dataChartBill() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> map = new HashMap<>();
        try {
            DateTime time = new DateTime();
            List<KeyValueStringIntegerModel> lsLabel = new ArrayList<>();
            date = time.plusDays(-6).toDate();
            for (int i = 0; i < 7; i++) {
                String label = sdf.format(date);
                KeyValueStringIntegerModel model = new KeyValueStringIntegerModel(label, BigDecimal.ZERO);
                lsLabel.add(model);
                date = time.plusDays(-5 + i).toDate();
            }
            List<ChartDashboardBillOrder> response = productBillService.listCountBillGrByDateBillId(date);
            for (KeyValueStringIntegerModel each : lsLabel) {
                for (ChartDashboardBillOrder res : response) {
                    if (each.getKey().compareTo(res.getDate()) == 0) {
                        each.setValue(res.getTotal());
                    }
                }
            }
            map.put("response", lsLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    @RequestMapping(value = "chartReject", method = RequestMethod.GET)
    public @ResponseBody
    String dataChartReject() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> map = new HashMap<>();
        try {
            DateTime time = new DateTime();
            List<KeyValueStringIntegerModel> lsLabel = new ArrayList<>();
            date = time.plusDays(-6).toDate();
            for (int i = 0; i < 7; i++) {
                String label = sdf.format(date);
                KeyValueStringIntegerModel model = new KeyValueStringIntegerModel(label, BigDecimal.ZERO);
                lsLabel.add(model);
                date = time.plusDays(-5 + i).toDate();
            }
            List<ChartDashboardBillOrder> response = rejectService.listCountRejectDashBoard(date);
            for (KeyValueStringIntegerModel each : lsLabel) {
                for (ChartDashboardBillOrder res : response) {
                    if (each.getKey().compareTo(res.getDate()) == 0) {
                        each.setValue(res.getTotal());
                    }
                }
            }
            map.put("response", lsLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

}
