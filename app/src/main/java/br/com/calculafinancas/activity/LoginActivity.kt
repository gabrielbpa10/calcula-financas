package br.com.calculafinancas.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import br.com.calculafinancas.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var criarNovoCadastro: TextView
    private lateinit var acessarPerfil: Button
    private var email: EditText? = null
    private var senha: EditText? = null
    private lateinit var mAuth: FirebaseAuth
    private var statusLogin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.activity_login_edit_text_email)
        senha = findViewById(R.id.activity_login_edit_text_senha)

        criarNovoCadastro = findViewById(R.id.activity_login_novo_cadastro)
        acessarPerfil = findViewById(R.id.activity_login_button_acessar)

        criarNovoCadastro.setOnClickListener(this)
        acessarPerfil.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.activity_login_novo_cadastro -> {
                var intent = Intent(this, RegistroActivity::class.java)
                startActivity(intent)
            }

            R.id.activity_login_button_acessar -> {
                if(email?.text!!.isNotEmpty() && senha?.text!!.isNotEmpty()){
                    mAuth = FirebaseAuth.getInstance()
                    mAuth.signInWithEmailAndPassword(email?.text.toString(),senha?.text.toString())
                            .addOnCompleteListener{
                                if(it.isSuccessful){
                                    //Toast.makeText(this,"Usuário existe",Toast.LENGTH_LONG).show()
                                    var intentPrincipal = Intent(this,
                                        PrincipalActivity::class.java)
                                    startActivity(intentPrincipal)
                                    finish()
                                } else {
                                    Toast.makeText(this,"Usuário ou senha incorretos.",Toast.LENGTH_LONG).show()
                                }
                    }
                } else {
                    Toast.makeText(this,"Existe algum campo vazio, por favor, verifique.",Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}