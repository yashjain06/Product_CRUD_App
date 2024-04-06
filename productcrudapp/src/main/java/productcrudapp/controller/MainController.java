package productcrudapp.controller;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import productcrudapp.dao.ProductDao;
import productcrudapp.model.Product;

@Controller
public class MainController {
	@Autowired
	private ProductDao productDao;

	@RequestMapping("/")
	public String home(Model m) {
		List<Product> product=productDao.getProducts();
		m.addAttribute("product", product);
		return "index";
	}
	
	
       @RequestMapping("/add-product")
   public String addProduct(Model m) {
	   System.out.println("Add-Pro");
	   m.addAttribute("title", "Add Product");
	   return "add_product";
   }
       @RequestMapping("/handle-product")
       public RedirectView handler(Product product,HttpServletRequest request) {
    	   RedirectView rv= new RedirectView();
    	   System.out.println(product);
    	   productDao.createProduct(product);
    	   rv.setUrl(request.getContextPath());
    	   return rv;
       }
        @RequestMapping("/delete/{id}")
       public RedirectView deletehandler(@PathVariable("id") int pid,HttpServletRequest req)
        {
        	productDao.deleteProduct(pid);
        	RedirectView rv= new RedirectView();
        	rv.setUrl(req.getContextPath()+"/");
        	return rv;
    	   
    	   
       }
        @RequestMapping("/update/{id}")
        public String renderUpdateForm(@PathVariable("id") int productId, Model model) {
            Product product = productDao.getProduct(productId);
            model.addAttribute("u_product", product);
            return "update";
        }
        
        @RequestMapping(path = "/update-product",method =RequestMethod.POST)
        public RedirectView update(Product product, HttpServletRequest req,Model m) {
        	RedirectView rv= new RedirectView();
        	
        		this.productDao.updateProduct(product); //in database
        		m.addAttribute("u_product", product);  //on view
                rv.setUrl(req.getContextPath()+"/");
        
        
        	
        	
        	return rv;
        	
        	
        }

}


