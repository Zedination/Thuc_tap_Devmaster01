package com.devmaster.restcontroller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devmaster.dao.LienHeDao;
import com.devmaster.entity.Datatables;
import com.devmaster.entity.LienHe;
import com.devmaster.entity.LoaiSanPham;
import com.devmaster.formdata.FormDataLienHe;

@RestController
public class RestLienHe {
	
	@Autowired
	LienHeDao lienHeDao;
	@Autowired
	public JavaMailSender emailSender;
	
    @PostMapping("/createlienhe")
    public String create(FormDataLienHe dataLienHe) {    	
    	return lienHeDao.createLienHe(dataLienHe);
    }
    
    @DeleteMapping("/deletelienhe")
    public String delete(@RequestParam("id") long id) {
    	return lienHeDao.deleteLienHe(id);
    }
    
	@GetMapping("/list-lienhe")
    public Datatables getDataTypeProduct() {
        List<List<String>> datatables = new ArrayList<List<String>>();
	    List<LienHe> temp = lienHeDao.getLienHe();
	    Datatables data = new Datatables();
	    temp.forEach(e->{
	    	SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
	    	Date date = new Date(Long.parseLong(e.getThoiGian()));
		    List<String> arr = Arrays.asList(String.valueOf(e.getId()),e.getHoTen(),e.getEmail(),e.getSoDienThoai(),e.getNoiDung(),sf.format(date));
		    datatables.add(arr);
	    });
	    data.setData(datatables);
	    return data;
    }
	@PostMapping("/rest-send-mail")
	public String sendMail(@RequestParam("nguoiNhan") String nguoiNhan, @RequestParam("subject") String subject, @RequestParam("content") String content) throws MessagingException {
		//System.setProperty("mail.mime.charset", "utf8");
		System.out.println(content);
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
		message.setContent(content, "text/html; charset=utf-8");
		helper.setTo(nguoiNhan);
        helper.setSubject(subject);
        this.emailSender.send(message);
        
        return "Email Sent!";
	}
}
