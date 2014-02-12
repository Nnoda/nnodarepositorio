package ebookshop;
import java.util.Vector;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

import ebookshop.Book;

public class ShoppingServlet extends HttpServlet 
{

  public void init(ServletConfig conf) throws ServletException  
  {
    super.init(conf);
  }

  public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
  {
    doPost(req, res);
  }

  public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
  {
    HttpSession session = req.getSession(true);
    @SuppressWarnings("unchecked")
    Vector<Book> shoplist = (Vector<Book>)session.getAttribute("ebookshop.cart");
    String do_this = req.getParameter("do_this");
    
    if (do_this == null) 
    {
      Vector<String> blist = new Vector<String>();
      blist.addElement("Learn HTML5 and JavaScript for iOS. Scott Preston $39.99");
      blist.addElement("Java 7 for Absolute Beginners. Jay Bryant $39.99");
      blist.addElement("Beginning Android 4. Livingston $39.99");
      blist.addElement("Pro Spatial with SQL Server 2012. Alastair Aitchison $59.99");
      blist.addElement("Beginning Database Design. Clare Churcher $34.99");
      blist.addElement("PRUEBA. Clare Churcher $34.99");
      session.setAttribute("ebookshop.list", blist);
      ServletContext    sc = getServletContext();
      RequestDispatcher rd = sc.getRequestDispatcher("/");
      rd.forward(req, res);
    }
    else 
    {
      if (do_this.equals("checkout"))  
      {
        float dollars = 0;
        int   books = 0;
        for (Book aBook : shoplist) 
        {
          float price = aBook.getPrice();
          int   qty = aBook.getQuantity();
          dollars += price * qty;
          books += qty;
        }
        req.setAttribute("dollars", new Float(dollars).toString());
        req.setAttribute("books", new Integer(books).toString());
        ServletContext    sc = getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher("/Checkout.jsp");
        rd.forward(req, res);
      }
      else 
      {
    	  if (do_this.equals("remove")) 
    	  {
	          String pos = req.getParameter("position");
	          shoplist.removeElementAt((new Integer(pos)).intValue());
          }
    	  else if (do_this.equals("add")) 
    	  {
	          Book aBook = getBook(req);
	          if(aBook != null)
	          {
		          if (shoplist == null) 
		          {
		          shoplist = new Vector<Book>();
		          shoplist.addElement(aBook);
		          }
		          else 
		          {
		        	  boolean found = false;
		        	  for (int i = 0; i < shoplist.size() && !found; i++) 
		        	  {
		        		  Book b = (Book)shoplist.elementAt(i);
		        		  if (b.getTitle().equals(aBook.getTitle())) 
		        		  {
		        			  b.setQuantity(b.getQuantity() + aBook.getQuantity());
		        			  shoplist.setElementAt(b, i);
		        			  found = true;
		        		  }
		        	  }
		        	  if (!found) 
		        	  {
		        		  shoplist.addElement(aBook);
		        	  }
		          }
	          }
    	  }
    	  else if (do_this.equals("update")) 
    	  {
    		  String num = req.getParameter("quantity");
    		  String pos = req.getParameter("position");
    		  Book abook = shoplist.get(Integer.parseInt(pos));
    		  try
    		  {
    			  abook.setQuantity(Integer.parseInt(num));
	              shoplist.setElementAt(abook, Integer.parseInt(pos));
	          }
	          catch(NumberFormatException e)
	          {
	        	  
	          }
	      }
    	  session.setAttribute("ebookshop.cart", shoplist);
    	  ServletContext sc = getServletContext();
    	  RequestDispatcher rd = sc.getRequestDispatcher("/");
    	  rd.forward(req, res);
        }
      }
    }

  private Book getBook(HttpServletRequest req) 
  {
		String myBook = req.getParameter("book");
	    int    n = myBook.indexOf('$');
	    String title = myBook.substring(0, n);
	    String price = myBook.substring(n+1);
	    String qty = req.getParameter("qty");
		try
		{
			return new Book(title, Float.parseFloat(price), Integer.parseInt(qty));
		}
		catch (NumberFormatException e)
		{
			return null;
		}
  }
}
