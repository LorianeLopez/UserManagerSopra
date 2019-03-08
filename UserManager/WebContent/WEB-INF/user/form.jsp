
<form action="Register" method="post" id="registration">
	<fieldset form="registration">
		<legend>Inscription</legend>
		<div>Formulaire d'inscription, les champs (*) sont requis :</div>

		<br> <label for="email">Adresse email <span id="etoile">*</span>
			:
		</label> <input class="requis" name="email" type="text"
			value="${form['email']}"> <span class="error">${errors['email']}</span><br>

		<label for="mdp">Mot de Passe <span id="etoile">*</span> :
		</label> <input class="requis" name="mdp" type="password"
			value="${form['mdp']}"> <span class="error">${errors['mdp']}</span><br>

		<label for="mdpConfirm">Confirmation Mot de Passe <span
			id="etoile">*</span> :
		</label> <input class="requis" name="mdpConfirm" type="password"
			value="${form['mdpConfirm']}"> <span class="error">${errors['mdpConfirm']}</span><br>

		<label for="nomUser">Nom Utilisateur : </label> <input name="nomUser"
			type="text" value="${form['nomUser']}"><br> <br>
		<button type="submit">Enregistrement</button>
		<p class="info">${actionMessage}</p>
	</fieldset>
</form>
<br>
