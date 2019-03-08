package llop.com.servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String VIEW_PAGE = "/WEB-INF/register.jsp";
	public static final String FIELD_EMAIL = "email";
	public static final String FIELD_PWD = "mdp";
	public static final String FIELD_PWD2 = "mdpConfirm";
	public static final String FIELD_NAME = "nomUser";
	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// On set le errorStatuts sur True pour ne pas que le car s'affiche
		request.setAttribute("errorStatus", true);
		this.getServletContext().getRequestDispatcher(VIEW_PAGE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("null")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// On récupère les valeurs entrées
		String email = request.getParameter(FIELD_EMAIL);
		String pwd1 = request.getParameter(FIELD_PWD);
		String pwd2 = request.getParameter(FIELD_PWD2);
		String name = request.getParameter(FIELD_NAME);
		String actionMessage = null;
		HttpSession session = request.getSession();

		Map<String, String> errors = new HashMap<String, String>();
		Map<String, String> form = new HashMap<String, String>();
		Map<String, User> users = (HashMap<String, User>) session.getAttribute("users");
		
		if(users==null){
			users = new HashMap<String, User>();
		}
		
		//Oon vérifie que l'email et les mdp sont correctement entrés
		String errMsg = validateEmail(email);
		String errMsg1 = validatePwd(pwd1, pwd2);
		
		//Si ce n'est pas le cas, on ne procède pas à l'inscription
		// Et on affiche un message d'erreur
		if (errMsg != null) {
			errors.put(FIELD_EMAIL, errMsg);
			form.put(FIELD_PWD, pwd1);
			form.put(FIELD_PWD2, pwd2);
			form.put(FIELD_NAME, name);
			actionMessage = "Echec de l'inscription";
			request.setAttribute("errorStatus", true);
		}else if(errMsg1 != null) {
			errors.put(FIELD_PWD, errMsg1);
			form.put(FIELD_EMAIL, email);
			form.put(FIELD_NAME, name);
			actionMessage = "Echec de l'inscription";
			request.setAttribute("errorStatus", true);
		}

		//Si aucune erreur n'est trouvé, on procède à l'inscription
		//On crée le User en cryptant son mdp avec un salt généré aléatoirement
		//Et on affiche un message informant du succès
		if (errMsg == null && errMsg1 == null) {
			form.put(FIELD_EMAIL, email);
			form.put(FIELD_PWD, pwd1);
			form.put(FIELD_PWD, pwd2);
			form.put(FIELD_NAME, name);
			actionMessage = "Succès de l'inscription";
			User newUser = new User();
			String salt = generate(6);
			try {
				salt = newUser.hashing(salt);
				pwd1 += salt;
				pwd1 = newUser.hashing(pwd1);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			newUser = new User(email, pwd1, name, salt);
			request.setAttribute("newUser", newUser);
			request.setAttribute("errorStatus", false);
			users.put(newUser.getEmail(), newUser);
			session.setAttribute("users", users);
		}

		// Set validation feedback attributes
		request.setAttribute("form", form);
		request.setAttribute("errors", errors);
		request.setAttribute("actionMessage", actionMessage);
		// Display page again !
		this.getServletContext().getRequestDispatcher(VIEW_PAGE).include(request, response);
	}

	private String validateEmail(String email) {
		if (email != null && email.trim().length() != 0) {
			if (!email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
				return ("Veuillez saisir une adresse mail valide");
			}
		}else {
			return ("Veuillez saisir une adresse mail valide");
		}
		return null;
	}

	private String validatePwd(String pwd1, String pwd2) {
		if(pwd1 == null || pwd1.length() < 3) {
			return("Le Mot de Passe doit contenir minimum 3 caractères.");
		}else if (!pwd1.equals(pwd2)) {
			return("Les mots de passe ne sont pas identiques");
		}
		return null;
	}
	
	public String generate(int length){
		    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		    String pass = "";
		    for(int x=0;x<length;x++)
		    {
		       int i = (int)Math.floor(Math.random() * 62);
		       pass += chars.charAt(i);
		    }
		    return pass;
	}

}