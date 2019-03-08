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
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static String VIEW_PAGE = "/WEB-INF/login.jsp";
	public static final String FIELD_EMAIL = "email";
	public static final String FIELD_PWD = "mdp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VIEW_PAGE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//On récupère les données entrées
		String email = request.getParameter(FIELD_EMAIL);
		String pwd = request.getParameter(FIELD_PWD);
		Map<String, String> form = new HashMap<String, String>();
		String actionMessage = null;
		
		//On récupère les informations des users présents dans la session
		HttpSession session = request.getSession();
		Map<String, User> users = (HashMap<String, User>) session.getAttribute("users");
		
		//Si les deux champs sont remplis et si on trouve l'email dans les users de la session
		//On crée le User, on crypte le mdp entré avec le salt correspondant à l'email
		//Et on compare le hash du mdp entré avec celui du mdp de l'user
		if(email.equals("")|| pwd.equals("")) {
			actionMessage="Veuillez remplir tout les champs";
		}else {
			if(users != null) {
				if(users.get(email) != null) {
					User actualUser = new User(users.get(email).getEmail(), users.get(email).getPwd(), users.get(email).getName(), users.get(email).getSalt());
					try {
						pwd += actualUser.getSalt();
						pwd = actualUser.hashing(pwd);
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}
					if(pwd.equals(actualUser.getPwd())) {
						//Si le hash des mdp sont identiques on procède à la connexion
						//Et on affiche un message de succès
						form.put(FIELD_EMAIL, email);
						form.put(FIELD_PWD, pwd);
						request.setAttribute("form", form);
						actionMessage = "Vous êtes connecté ! ";
						if(users.get(email).getName() != null) {
							actionMessage += "Bienvenue " + users.get(email).getName() + " !";
						}
			//Sinon, on affiche un message d'erreur
					}else {
						actionMessage = "Erreur, les données sont erronées.";
					}
				}else {
					actionMessage = "Erreur, les données sont erronées.";
				}
			}else {
				actionMessage = "Veuillez vous inscrire.";
			}
		}
		request.setAttribute("actionMessage", actionMessage);
		// Display page again !
		this.getServletContext().getRequestDispatcher(VIEW_PAGE).include(request, response);
	}

}
