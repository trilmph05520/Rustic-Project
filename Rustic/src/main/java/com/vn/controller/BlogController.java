package com.vn.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vn.common.Constants;
import com.vn.common.FileUtils;
import com.vn.jpa.Blog;
import com.vn.jpa.Category;
import com.vn.jpa.Product;
import com.vn.model.BlogModel;
import com.vn.model.ProductModel;
import com.vn.service.BlogService;

@Controller
@RequestMapping(value = "/blog/")
public class BlogController {

	@Resource
	private BlogService blogService;
	

	@Value("${Rustic.root.folder}")
	private String ROOT_FOLDER;

	@Value("${host.address}")
	private String HOST_ADDRESS;

	@Value("${main.images.folder}")
	private String MAIN_ADDRESS;

	@Value("${sub.images.folder}")
	private String SUB_ADDRESS;
	
	@RequestMapping(value = "list.html", method = { RequestMethod.GET, RequestMethod.POST })
	@PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
	public String list(Model model, HttpSession session, HttpServletRequest request,
			@RequestParam(value = "title", defaultValue = "") String title, Pageable pageable) {
		Sort sort = new Sort(Sort.Direction.DESC, "id");
		Pageable _pageable = new PageRequest(pageable.getPageNumber(), Constants.Paging.SIZE, sort);
		Page<Blog> page = blogService.findAllByTitle(title, _pageable);
		model.addAttribute("page", page);
		return "admin/blog/list";
	}
	
	
	@GetMapping("add")
	@PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
	private String add(ModelMap model) {
		model.put("blog", new Blog());
		return "admin/blog/add";
	}
	
	@RequestMapping(value = "add.html", method = RequestMethod.POST)
	@PreAuthorize("hasAnyAuthority('Administrators','Staffs')")
	public @ResponseBody String addBlog(@RequestBody(required = false) BlogModel model) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Map<String, Object> responseMap = new HashMap<>();
		String filePathMain = ROOT_FOLDER + MAIN_ADDRESS;
		String sorceWebPathMain = HOST_ADDRESS + MAIN_ADDRESS;
		String filePathSub = ROOT_FOLDER + SUB_ADDRESS;
		String sorceWebPathSub = HOST_ADDRESS + SUB_ADDRESS;
		try {
			FileUtils.Result resultMain = FileUtils.storageFile(filePathMain, model.getMainImg(), false, false,
					sorceWebPathMain);
			FileUtils.Result resultSub = FileUtils.storageFile(filePathSub, model.getSubImg(), false, false,
					sorceWebPathSub);
			Blog blog = new Blog();
			blog.setCreated(new Date());
			blog.setMainImg(resultMain.getResult());
			blog.setSubImg(resultSub.getResult());
			blog.setMainDescription(model.getMainDescription());
			blog.setSubDescription(model.getSubDescription());
			blog.setTitle(model.getTitle());
			blogService.insert(blog);
			
			responseMap.put("success", "Thêm sản phẩm thành công");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gson.toJson(responseMap);
	}
	
}
