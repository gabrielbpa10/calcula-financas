package br.com.calculafinancas.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.com.calculafinancas.R
import br.com.calculafinancas.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistroActivity : AppCompatActivity(), View.OnClickListener {

    private val NOME_BAR_TELA: String = "Cadastro"
    private var nomeEditText: EditText? = null
    private var sobrenomeEditText: EditText? = null
    private var telefoneEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var senhaEditText: EditText? = null
    private var botaoSalvar: Button? = null
    private lateinit var mAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        setTitle(NOME_BAR_TELA)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        iniciarComponentesTela()

        botaoSalvar?.setOnClickListener(this)

    }

    fun iniciarComponentesTela() {
        nomeEditText = findViewById(R.id.activity_registro_edit_text_nome)
        sobrenomeEditText = findViewById(R.id.activity_registro_edit_text_sobrenome)
        telefoneEditText = findViewById(R.id.activity_registro_edit_text_telefone)
        emailEditText = findViewById(R.id.activity_registro_edit_text_email)
        senhaEditText = findViewById(R.id.activity_registro_edit_text_senha)
        botaoSalvar = findViewById(R.id.activity_registro_button_salvar)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.activity_registro_button_salvar -> {
                // Terminar de refatorar o cadastro de usuário

                val nome = nomeEditText?.text.toString()
                val sobrenome = sobrenomeEditText?.text.toString()
                val telefone = telefoneEditText?.text.toString()
                val email = emailEditText?.text.toString()
                val senha = senhaEditText?.text.toString()

                if(nome.isEmpty()){
                    Toast.makeText(this,"Campo nome em branco",Toast.LENGTH_SHORT).show()
                }

                if(sobrenome.isEmpty()){
                    Toast.makeText(this,"Campo sobrenome em branco",Toast.LENGTH_SHORT).show()
                }

                if(telefone.isEmpty()){
                    Toast.makeText(this,"Campo telefone em branco",Toast.LENGTH_SHORT).show()
                }

                if(email.isEmpty()){
                    Toast.makeText(this,"Campo email em branco",Toast.LENGTH_SHORT).show()
                }

                if(senha.isEmpty()){
                    Toast.makeText(this,"Campo senha em branco",Toast.LENGTH_SHORT).show()
                }

                if(nome.isNotEmpty() && sobrenome.isNotEmpty()  && telefone.isNotEmpty()  &&
                    email.isNotEmpty() && senha.isNotEmpty()) {

                    val handler = Handler(Looper.getMainLooper())
                    mAuth = FirebaseAuth.getInstance()
                    mAuth.createUserWithEmailAndPassword(email, senha)
                        .addOnCompleteListener{
                            if(it.isSuccessful){
                                val usuario = Usuario(nome,sobrenome,telefone,email)

                                firebaseDatabase = FirebaseDatabase.getInstance()
                                val ref = firebaseDatabase.getReference("usuarios/${mAuth.uid}")
                                ref.setValue(usuario)

                                handler.post{
                                    Toast.makeText(applicationContext,
                                        "O usuário ${nome} foi cadastrado com sucesso!",
                                        Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            } else{
                                handler.post{
                                    Toast.makeText(
                                        applicationContext,
                                        "Usuário não cadastrado, por favor tente mais tarde.",
                                        //it.exception?.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                }
            }
        }
    }
}