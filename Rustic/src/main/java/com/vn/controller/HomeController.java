package com.vn.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vn.common.Constants;
import com.vn.common.ThymeleafUtil;
import com.vn.config.GoogleMailSender;
import com.vn.jpa.AuthUser;
import com.vn.jpa.Bill;
import com.vn.jpa.Category;
import com.vn.jpa.Infomation;
import com.vn.jpa.Product;
import com.vn.jpa.Product_Bill;
import com.vn.jpa.Report;
import com.vn.jpa.Review;
import com.vn.model.AuthUserModel;
import com.vn.model.BillProfileModel;
import com.vn.model.Cart;
import com.vn.model.InfomationModel;
import com.vn.model.ProductQuickViewModel;
import com.vn.service.AuthUserService;
import com.vn.service.BankInfoService;
import com.vn.service.BillService;
import com.vn.service.CategoryService;
import com.vn.service.InfomationService;
import com.vn.service.ProductService;
import com.vn.service.Product_BillService;
import com.vn.service.ReviewService;
import com.vn.validation.service.InfomationFormValidator;

@Controller
public class HomeController {

	@Resource
	private CategoryService categoryService;

	@Resource
	private ProductService productService;

	@Resource
	private ReviewService reviewService;

	@Resource
	private AuthUserService authUserService;

	@Resource
	private InfomationService infomationService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Resource
	private JavaMailSender mailSender;

	@Resource
	private InfomationFormValidator infoFormValidator;

	@Resource
	private BillService billService;

	@Resource
	private Product_BillService prBillService;

	@Resource
	private BankInfoService bankService;

	@ModelAttribute("report")
	public Report report(Model model) {
		model.addAttribute("mapError", new HashedMap<String, String>());
		return new Report();
	}

	@RequestMapping(value = "/home/login.html", method = RequestMethod.GET)
	public ModelAndView loginPage(Model model, Pageable pageable) {
		Map<String, String> mapError = new HashedMap<String, String>();
		model.addAttribute("athUser", new AuthUserModel());
		model.addAttribute("mapError", mapError);
		ModelAndView modelAndView = new ModelAndView("home/login");
		return modelAndView;
	}

	@RequestMapping(value = "/home/login.html", method = RequestMethod.POST)
	public String loginPageSuccsess(HttpSession session, @RequestParam("user") String user,
			@RequestParam("password") String pass, Model model) {
		if (session.getAttribute("userLogin") != null) {
			return "redirect:/";
		} else {
			AuthUser authUser = authUserService.findByUsername(user);
			if (authUser != null) {
				if (passwordEncoder.matches(pass, authUser.getPassword())) {
					session.setAttribute("userLogin", authUser);
					return "redirect:/";
				}
				model.addAttribute("errorLogin", "Sai tài khoản hoặc mật khẩu");
			} else {
				model.addAttribute("errorLogin", "Sai tài khoản hoặc mật khẩu");
			}
			return "home/login";
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ModelAndView indexs(Model model, Pageable pageable, HttpSession session) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
		Pageable _pageable = new PageRequest(pageable.getPageNumber(), 8, sort);
		Page<Product> product = productService.findAllByIsdelete("N", _pageable);
		List<Product> newProduct = productService.lsProductDateDesc();
		List<Category> category = categoryService.findAllByIsDeleteAndIsActive("N", "Y");
		Map<Long, List> mapLsId = new HashMap<>();
		List<Category> lstCatePr = new ArrayList<Category>();
		for (Category each : category) {
			if (each.getParent() == null) {
				List ls = new ArrayList();
				ls.add(each.getName());
				List lsCategoryChildren = new ArrayList();
				ls.add(lsCategoryChildren);
				mapLsId.put(each.getId(), ls);
				lstCatePr.add(each);
			}
		}
		session.setAttribute("categoryNavPr", lstCatePr);
		for (Category eachCateChildren : category) {
			if (eachCateChildren.getParent() != null) {
				if (mapLsId.get(eachCateChildren.getParent().getId()).get(1) != null) {
					ArrayList lsChildren = (ArrayList) mapLsId.get(eachCateChildren.getParent().getId()).get(1);
					List lsCategoryInfo = new ArrayList();
					lsCategoryInfo.add(eachCateChildren.getId());
					lsCategoryInfo.add(eachCateChildren.getName());
					lsChildren.add(lsCategoryInfo);
				}
			}
		}
		session.setAttribute("categoryNav", mapLsId);
		model.addAttribute("newProduct", newProduct);
		model.addAttribute("page", product);
		ModelAndView modelAndView = new ModelAndView("home/index");
		return modelAndView;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = { "/home/shop.html" }, method = RequestMethod.GET)
	public ModelAndView shop(Model model, Pageable pageable, HttpSession session) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
		Pageable _pageable = new PageRequest(pageable.getPageNumber(), 8, sort);
		Page<Product> product = productService.findAllByIsdelete("N", _pageable);
		List<Product> newProduct = productService.lsProductDateDesc();
		List<Category> category = categoryService.findAllByIsDeleteAndIsActive("N", "Y");
		Map<Long, List> mapLsId = new HashMap<>();
		List<Category> lstCatePr = new ArrayList<Category>();
		for (Category each : category) {
			if (each.getParent() == null) {
				List ls = new ArrayList();
				ls.add(each.getName());
				List lsCategoryChildren = new ArrayList();
				ls.add(lsCategoryChildren);
				mapLsId.put(each.getId(), ls);
				lstCatePr.add(each);
			}
		}
		session.setAttribute("categoryNavPr", lstCatePr);
		for (Category eachCateChildren : category) {
			if (eachCateChildren.getParent() != null) {
				if (mapLsId.get(eachCateChildren.getParent().getId()).get(1) != null) {
					ArrayList lsChildren = (ArrayList) mapLsId.get(eachCateChildren.getParent().getId()).get(1);
					List lsCategoryInfo = new ArrayList();
					lsCategoryInfo.add(eachCateChildren.getId());
					lsCategoryInfo.add(eachCateChildren.getName());
					lsChildren.add(lsCategoryInfo);
				}
			}
		}
		session.setAttribute("categoryNav", mapLsId);
		model.addAttribute("newProduct", newProduct);
		model.addAttribute("page", product);
		ModelAndView modelAndView = new ModelAndView("home/shop");
		return modelAndView;

	}

	@RequestMapping(value = "/home/{id}/single-product.html", method = { RequestMethod.GET })
	public ModelAndView singleProduct(Model model, @PathVariable("id") Long id) {
		Product product = productService.findOne(id);
		List<Product> newProduct = productService.lsProductDateDesc();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		if (product.getSubImg() != null || product.getSubImg() != "") {
			String[] subImg = gson.fromJson(product.getSubImg(), new TypeToken<String[]>() {
			}.getType());
			model.addAttribute("subImg", subImg);
		}
		List<Review> lsReview = reviewService.findAllByProductIdAndStatus(product.getId(),
				Review.status.ACTIVE.value());
		List<Product> productRelationship = productService
				.findProductByCategoryIdAndIsdelete(product.getCategory().getId(), "N");
		Long countRate = reviewService.countRateByProductId(id);
		model.addAttribute("countRate", countRate);
		model.addAttribute("newProduct", newProduct);
		model.addAttribute("product", product);
		model.addAttribute("proRelationship", productRelationship);
		model.addAttribute("review", lsReview);
		ModelAndView modelAndView = new ModelAndView("home/product-single");
		return modelAndView;
	}

	@RequestMapping(value = "/home/{id}/product.html", method = { RequestMethod.GET })
	public String product(Model model, @PathVariable("id") Long id, Pageable pageable) {
		Category category = categoryService.findOne(id);
		Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
		Pageable _page = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
		Page<Product> page = productService.findAllByCategoryIdAndIsdelete(id, _page, "N");
		model.addAttribute("name", category.getName());
		model.addAttribute("page", page);
		return "home/product";
	}

	@RequestMapping(value = "/home/{id}/list/product.html", method = { RequestMethod.GET })
	public String listProduct(Model model, @PathVariable("id") Long parentId, Pageable pageable) {
		try {
			List<BigInteger> lsCate = categoryService.getListCategoryById(parentId, "Y", "N");
			List<Long> lsLong = new ArrayList<>();
			for (BigInteger each : lsCate) {
				lsLong.add(each.longValue());
			}
			Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
			Pageable _page = new PageRequest(pageable.getPageNumber(), 8, sort);
			Page<Product> page = productService.findByCategoryParentAndIsdelete(lsLong, "N", _page);
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "home/product";
	}

	@RequestMapping(value = "/home/single-product.html", method = RequestMethod.GET)
	public @ResponseBody String category(@RequestParam("id") Long id) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Product product = productService.findOne(id);
		ProductQuickViewModel viewModel = new ProductQuickViewModel();
		viewModel.setDescription(product.getDescription());
		viewModel.setName(product.getName());
		viewModel.setPrice(product.getPrice());
		viewModel.setPriceSale(product.getPriceSale());
		viewModel.setMainImg(product.getMainImg());
		viewModel.setStatus(product.getStatus());
		viewModel.setInfo(product.getInfo());
		viewModel.setQuantity(product.getQuantity());
		return gson.toJson(viewModel);
	}

	@RequestMapping(value = "/home/cart.html", method = RequestMethod.GET)
	public ModelAndView viewCart(HttpSession session, Pageable pageable, Model model) {
		AuthUser authUser = (AuthUser) session.getAttribute("userLogin");
		if (authUser != null) {
			Infomation infomation = infomationService.findByAuthUserId(authUser.getId());
			model.addAttribute("name", authUser.getFullName());
			model.addAttribute("email", authUser.getEmail());
			if (infomation != null) {
				model.addAttribute("mobile", infomation.getPhone());
				model.addAttribute("address", infomation.getAddress());
			}
		}
		ModelAndView modelAndView = new ModelAndView("home/cart");
		if (session.getAttribute("myCartItems") == null) {
			Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
			Pageable _pageable = new PageRequest(pageable.getPageNumber(), 8, sort);
			Page<Product> product = productService.findAllByIsdelete("N", _pageable);
			List<Product> newProduct = productService.lsProductDateDesc();
			List<Category> category = categoryService.findAllByIsDeleteAndIsActive("N", "Y");
			Map<Long, List> mapLsId = new HashMap<>();
			List<Category> lstCatePr = new ArrayList<Category>();
			for (Category each : category) {
				if (each.getParent() == null) {
					List ls = new ArrayList();
					ls.add(each.getName());
					List lsCategoryChildren = new ArrayList();
					ls.add(lsCategoryChildren);
					mapLsId.put(each.getId(), ls);
					lstCatePr.add(each);
				}
			}
			session.setAttribute("categoryNavPr", lstCatePr);
			for (Category eachCateChildren : category) {
				if (eachCateChildren.getParent() != null) {
					ArrayList lsChildren = (ArrayList) mapLsId.get(eachCateChildren.getParent().getId()).get(1);
					List lsCategoryInfo = new ArrayList();
					lsCategoryInfo.add(eachCateChildren.getId());
					lsCategoryInfo.add(eachCateChildren.getName());
					lsChildren.add(lsCategoryInfo);
				}
			}
			session.setAttribute("categoryNav", mapLsId);
			model.addAttribute("newProduct", newProduct);
			model.addAttribute("page", product);
			modelAndView = new ModelAndView("redirect:/");
		} else if (((HashMap<Long, Cart>) session.getAttribute("myCartItems")).isEmpty()) {
			Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
			Pageable _pageable = new PageRequest(pageable.getPageNumber(), 8, sort);
			Page<Product> product = productService.findAllByIsdelete("N", _pageable);
			List<Product> newProduct = productService.lsProductDateDesc();
			List<Category> category = categoryService.findAllByIsDeleteAndIsActive("N", "Y");
			Map<Long, List> mapLsId = new HashMap<>();
			List<Category> lstCatePr = new ArrayList<Category>();
			for (Category each : category) {
				if (each.getParent() == null) {
					List ls = new ArrayList();
					ls.add(each.getName());
					List lsCategoryChildren = new ArrayList();
					ls.add(lsCategoryChildren);
					mapLsId.put(each.getId(), ls);
					lstCatePr.add(each);
				}
			}
			session.setAttribute("categoryNavPr", lstCatePr);
			for (Category eachCateChildren : category) {
				if (eachCateChildren.getParent() != null) {
					ArrayList lsChildren = (ArrayList) mapLsId.get(eachCateChildren.getParent().getId()).get(1);
					List lsCategoryInfo = new ArrayList();
					lsCategoryInfo.add(eachCateChildren.getId());
					lsCategoryInfo.add(eachCateChildren.getName());
					lsChildren.add(lsCategoryInfo);
				}
			}
			session.setAttribute("categoryNav", mapLsId);
			model.addAttribute("newProduct", newProduct);
			model.addAttribute("page", product);
			modelAndView = new ModelAndView("redirect:/");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/home/about.html", method = RequestMethod.GET)
	public ModelAndView viewAbout() {
		ModelAndView modelAndView = new ModelAndView("home/about");
		return modelAndView;
	}

	@RequestMapping(value = "/home/contact.html", method = RequestMethod.GET)
	public ModelAndView viewContact(Model model) {
//        model.addAttribute("report", new Report());
//        model.addAttribute("mapError", new HashedMap<String, String>());
		ModelAndView modelAndView = new ModelAndView("home/contact");
		return modelAndView;
	}

	@RequestMapping(value = "/home/blog.html", method = RequestMethod.GET)
	public ModelAndView viewBlog(Model model) {
		ModelAndView modelAndView = new ModelAndView("home/blog");
		return modelAndView;
	}

	@RequestMapping(value = "/home/register.html", method = RequestMethod.GET)
	public ModelAndView viewRegister(Model model) {
		Map<String, String> mapError = new HashedMap<String, String>();
		model.addAttribute("authUser", new AuthUserModel());
		model.addAttribute("mapError", mapError);
		ModelAndView modelAndView = new ModelAndView("home/register");
		return modelAndView;
	}

	@RequestMapping(value = "/home/faq.html", method = RequestMethod.GET)
	public ModelAndView viewFAQ(Model model) {
		ModelAndView modelAndView = new ModelAndView("home/faq");
		return modelAndView;
	}

	@RequestMapping(value = "/home/profile.html", method = RequestMethod.GET)
	public ModelAndView profilePage(HttpSession session, Model model, Pageable pageable) {
		try {
			if (session.getAttribute("userLogin") == null) {
				Map<String, String> mapError = new HashedMap<String, String>();
				model.addAttribute("authUser", new AuthUserModel());
				model.addAttribute("mapError", mapError);
				ModelAndView modelAndView = new ModelAndView("home/login");
				return modelAndView;
			} else {
				AuthUser authUser = null;
				Infomation inf = null;
				InfomationModel infModel = new InfomationModel();
				if (session.getAttribute("userLogin") != null) {
					authUser = (AuthUser) session.getAttribute("userLogin");
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
				Long id = authUser.getId();
				Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "createDate"));
				Pageable _page = new PageRequest(pageable.getPageNumber(), 10, sort);
				Page<BillProfileModel> lsBill = billService.pageBillForShowProfileUser(id, _page);
				if (lsBill.getContent() != null) {
					model.addAttribute("lsBill", lsBill);
				}
				model.addAttribute("profile", infModel);
				model.addAttribute("bankInfo", bankService.findAll());
				ModelAndView modelAndView = new ModelAndView("home/profile");
				return modelAndView;
			}
		} catch (Exception e) {
			ModelAndView modelAndView = new ModelAndView("home/profile");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/home/profile.html", method = RequestMethod.POST)
	public String profileSave(HttpSession session, Model model,
			@ModelAttribute("profile") @Valid InfomationModel infomationModel, BindingResult result,
			Pageable pageable) {
		Map<String, String> mapError = new HashedMap<String, String>();
		AuthUser authUser = authUserService.findOne(((AuthUser) session.getAttribute("userLogin")).getId());
		try {
			infoFormValidator.validateReportForm(infomationModel, result);
			if (result.hasErrors()) {
				for (Object obj : result.getAllErrors()) {
					if (obj instanceof ObjectError) {
						mapError.put(((ObjectError) obj).getCode(), ((ObjectError) obj).getDefaultMessage());
					}
				}
				Long id = authUser.getId();
				Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "createDate"));
				Pageable _page = new PageRequest(pageable.getPageNumber(), 20, sort);
				Page<BillProfileModel> lsBill = billService.pageBillForShowProfileUser(id, _page);
				if (lsBill.getContent() != null) {
					model.addAttribute("lsBill", lsBill);

				}
				model.addAttribute("mapError", mapError);
				model.addAttribute("bankInfo", bankService.findAll());
				return "home/profile";
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
				authUserService.update(authUser);
				session.setAttribute("userLogin", authUser);
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
			Long id = authUser.getId();
			Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "createDate"));
			Pageable _page = new PageRequest(pageable.getPageNumber(), 20, sort);
			Page<BillProfileModel> lsBill = billService.pageBillForShowProfileUser(id, _page);
			if (lsBill.getContent() != null) {
				model.addAttribute("lsBill", lsBill);

			}
			model.addAttribute("mapError", mapError);
			model.addAttribute("bankInfo", bankService.findAll());
			mapError.put("errorProfile", "Lỗi không xác định");
			model.addAttribute("mapError", mapError);
			return "home/profile";
		}
		return "redirect:/home/profile.html";
	}

	@RequestMapping(value = "/home/logout.html", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("userLogin");
		return "redirect:/home/login.html";
	}

	@RequestMapping(value = "/home/{user}/reset.html", method = RequestMethod.GET)
	public ModelAndView resetPassword(Model model, @PathVariable("user") String user, HttpSession session) {
		if (session.getAttribute("userLogin") == null) {
			Map<String, String> mapError = new HashedMap<String, String>();
			model.addAttribute("athUser", new AuthUserModel());
			model.addAttribute("mapError", mapError);
			ModelAndView modelAndView = new ModelAndView("home/login");
			return modelAndView;
		} else {
			ModelAndView modelAndView = new ModelAndView("home/reset-password");
			return modelAndView;
		}
	}

	@RequestMapping(value = "/home/reset.html", method = RequestMethod.POST)
	public @ResponseBody String reset(Model model, @RequestParam("user") String user,
			@RequestParam("oldPass") String oldPass, @RequestParam("newPass") String newPass, HttpSession session) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Map<String, Object> respone = new HashMap<>();
		try {
			AuthUser authUser = authUserService.findByUsername(user);
			if (authUser != null) {
				if (!passwordEncoder.matches(oldPass, authUser.getPassword())) {
					respone.put("oldPassErr", "Nhập sai mật khẩu hiện tại");
				}
				if (passwordEncoder.matches(newPass, authUser.getPassword())) {
					respone.put("newPassErr", "Mật khẩu mới trùng mật khẩu hiện tại");
				}
				if (respone.size() == 0) {
					authUser.setPassword(passwordEncoder.encode(newPass));
					authUserService.update(authUser);
					respone.put("success", "Đổi mật khẩu thành công");
					session.removeAttribute("userLogin");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(respone);
	}

	@RequestMapping(value = "/home/forgetPassword.html", method = RequestMethod.POST)
	public @ResponseBody String forgetPassword(@RequestParam("email") String email) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Map<String, Object> response = new HashMap<>();
		try {
			AuthUser authUser = authUserService.findByEmail(email);
			if (authUser != null) {
				Map<String, Object> responseMap = new HashMap<>();
				String pass = RandomStringUtils.randomAlphanumeric(8);
				GoogleMailSender mailSender = new GoogleMailSender();
				if (authUser.getFullName() != null) {
					responseMap.put("name", authUser.getFullName());
				} else {
					responseMap.put("name", authUser.getUserName());
				}
				responseMap.put("password", pass);
				responseMap.put("userName", authUser.getUserName());
				boolean statusSend = false;
//                new Thread(() -> {
				try {
					final String htmlContent = ThymeleafUtil.getHtmlContentInClassPath("html/MailForgetPassword.html",
							(HashMap<String, Object>) responseMap);
					mailSender.sendSimpleMailWarningTLS("Rustic<trilmph05520@fpt.edu.vn>", email,
							"[Rustic] Cấp Lại Mật Khẩu", htmlContent);
					statusSend = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
//                }).start();
				if (statusSend) {
					authUser.setPassword(passwordEncoder.encode(pass));
					authUserService.update(authUser);
					response.put("success", "Quý khách vui lòng truy cập email để nhận lại mật khẩu mới");
				} else {
					response.put("err", "Lỗi hệ thống");
				}
			} else {
				response.put("err", "Không tìm thấy địa chỉ email");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(response);
	}

	@RequestMapping(value = "/home/search.html", method = { RequestMethod.GET, RequestMethod.POST })
	public String search(@RequestParam(value = "name", defaultValue = "") String name, Pageable pageable, Model model,
			HttpSession session, HttpServletRequest request) {
		try {
			if (Strings.isNullOrEmpty(name) && request.getMethod().equalsIgnoreCase("GET")) {
				name = (String) session.getAttribute("search");
			}
			Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "name"));
			Pageable _page = new PageRequest(pageable.getPageNumber(), 8, sort);
			Page<Product> page = productService.findAllByNameIsLikeAndIsdelete(name, "N", _page);
			session.setAttribute("search", name);
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "home/product";
	}

	@RequestMapping(value = "/home/review/{id}.html")
	public String revire(Model model, @PathVariable("id") Long id, HttpSession session) {
		if (reviewService.countRateByBillId(id) > 0) {
			return "redirect:/";
		} else {
			List<Product_Bill> lstPrBill = prBillService.findByBill_Id(id);
			model.addAttribute("lstBillPr", lstPrBill);
			model.addAttribute("rate", 1);
			model.addAttribute("description", "");
			session.setAttribute("idBill", id);
			return "home/review";
		}
	}

	@RequestMapping(value = "/home/checkOut.html", method = RequestMethod.GET)
	public ModelAndView checkOut(Model model, Pageable pageable, HttpSession session) {
		AuthUser authUser = (AuthUser) session.getAttribute("userLogin");
		if (authUser != null) {
			Infomation infomation = infomationService.findByAuthUserId(authUser.getId());
			model.addAttribute("name", authUser.getFullName());
			model.addAttribute("email", authUser.getEmail());
			if (infomation != null) {
				model.addAttribute("mobile", infomation.getPhone());
				model.addAttribute("address", infomation.getAddress());
			}
		}
		ModelAndView modelAndView = new ModelAndView("home/check-out");
		if (session.getAttribute("myCartItems") == null) {
			Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
			Pageable _pageable = new PageRequest(pageable.getPageNumber(), 8, sort);
			Page<Product> product = productService.findAllByIsdelete("N", _pageable);
			List<Product> newProduct = productService.lsProductDateDesc();
			List<Category> category = categoryService.findAllByIsDeleteAndIsActive("N", "Y");
			Map<Long, List> mapLsId = new HashMap<>();
			List<Category> lstCatePr = new ArrayList<Category>();
			for (Category each : category) {
				if (each.getParent() == null) {
					List ls = new ArrayList();
					ls.add(each.getName());
					List lsCategoryChildren = new ArrayList();
					ls.add(lsCategoryChildren);
					mapLsId.put(each.getId(), ls);
					lstCatePr.add(each);
				}
			}
			session.setAttribute("categoryNavPr", lstCatePr);
			for (Category eachCateChildren : category) {
				if (eachCateChildren.getParent() != null) {
					ArrayList lsChildren = (ArrayList) mapLsId.get(eachCateChildren.getParent().getId()).get(1);
					List lsCategoryInfo = new ArrayList();
					lsCategoryInfo.add(eachCateChildren.getId());
					lsCategoryInfo.add(eachCateChildren.getName());
					lsChildren.add(lsCategoryInfo);
				}
			}
			session.setAttribute("categoryNav", mapLsId);
			model.addAttribute("newProduct", newProduct);
			model.addAttribute("page", product);
			modelAndView = new ModelAndView("redirect:/");
		} else if (((HashMap<Long, Cart>) session.getAttribute("myCartItems")).isEmpty()) {
			Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
			Pageable _pageable = new PageRequest(pageable.getPageNumber(), 8, sort);
			Page<Product> product = productService.findAllByIsdelete("N", _pageable);
			List<Product> newProduct = productService.lsProductDateDesc();
			List<Category> category = categoryService.findAllByIsDeleteAndIsActive("N", "Y");
			Map<Long, List> mapLsId = new HashMap<>();
			List<Category> lstCatePr = new ArrayList<Category>();
			for (Category each : category) {
				if (each.getParent() == null) {
					List ls = new ArrayList();
					ls.add(each.getName());
					List lsCategoryChildren = new ArrayList();
					ls.add(lsCategoryChildren);
					mapLsId.put(each.getId(), ls);
					lstCatePr.add(each);
				}
			}
			session.setAttribute("categoryNavPr", lstCatePr);
			for (Category eachCateChildren : category) {
				if (eachCateChildren.getParent() != null) {
					ArrayList lsChildren = (ArrayList) mapLsId.get(eachCateChildren.getParent().getId()).get(1);
					List lsCategoryInfo = new ArrayList();
					lsCategoryInfo.add(eachCateChildren.getId());
					lsCategoryInfo.add(eachCateChildren.getName());
					lsChildren.add(lsCategoryInfo);
				}
			}
			session.setAttribute("categoryNav", mapLsId);
			model.addAttribute("newProduct", newProduct);
			model.addAttribute("page", product);
			modelAndView = new ModelAndView("redirect:/");
		}
		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/home/reviewSubmit.html", method = RequestMethod.POST)
	public @ResponseBody String submit(@RequestBody List<Object> lst, HttpSession session) {
		try {
			Review review = null;
			Product product = null;
			LinkedHashMap<String, String> map = null;
			long id = (long) session.getAttribute("idBill");
			Bill bill = billService.findOne(id);
			Date date = new Date();
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			for (Object obj : lst) {
				map = (LinkedHashMap<String, String>) obj;
				review = new Review();
				product = productService.findOne(Long.parseLong(map.get("id")));
				review.setProduct(product);
				review.setRate(Integer.parseInt(map.get("rate")));
				review.setDescription(map.get("description"));
				review.setEmail(bill.getEmail());
				review.setName(bill.getName());
				review.setStatus(0);
				review.setCreateDate(date);
				review.setBill(bill);
				reviewService.insert(review);
			}
		} catch (JSONException _instance) {
		}

		return "Json String";
	}

	class review {
		int id;
		int rate;
		String description;
	}
}
