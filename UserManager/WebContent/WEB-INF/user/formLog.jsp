
<form action="Login" method="post" id="login">
	<fieldset form="login">
		<legend>Connexion</legend>
		<div>Formulaire de connexion, les champs (*) sont requis :</div>

		<br> <label for="email">Adresse email <span id="etoile">*</span>
			:
		</label> <input class="requis" name="email" type="text"
			value="${formLog['email']}"> <span class="error">${errors['email']}</span><br>

		<label for="mdp">Mot de Passe <span id="etoile">*</span> :
		</label> <input class="requis" name="mdp" type="password"
			value="${formLog['mdp']}"> <span class="error">${errors['mdp']}</span><br>

		<br>
		<button type="submit">Connexion</button>
		<p class="info">${actionMessage}</p>
	</fieldset>
</form>
<br>
