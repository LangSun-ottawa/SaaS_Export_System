package com.langsun.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.langsun.domain.company.Company;
import com.langsun.service.company.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplyController {

	@Reference
	private CompanyService companyService;

	@RequestMapping("/apply.do")
	public @ResponseBody String apply(Company company) {
		System.out.println("controller执行了");
		try {
			companyService.save(company);
			return "1";
		}catch (Exception e){
			e.printStackTrace();
		}
		return "2";
	}
}
