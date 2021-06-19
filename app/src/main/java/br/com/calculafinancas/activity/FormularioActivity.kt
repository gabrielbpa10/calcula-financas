package br.com.calculafinancas.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import br.com.calculafinancas.R
import br.com.calculafinancas.model.Transacao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FormularioActivity : AppCompatActivity(), View.OnClickListener {

    private val NOME_BAR_TELA: String = "Cadastro"
    private lateinit var descricaoEditText: EditText
    private lateinit var valorEditText: EditText
    private lateinit var despesaCheckBox: CheckBox
    private lateinit var receitaCheckBox: CheckBox
    private lateinit var botaoSalvar: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)
        setTitle(NOME_BAR_TELA)
        iniciarComponentesTela()
        iniciarConexaoFireBase()

        botaoSalvar.setOnClickListener(this)
    }

    fun iniciarComponentesTela() {
        descricaoEditText = findViewById(R.id.activity_formulario_editText_descricao)
        valorEditText = findViewById(R.id.activity_formulario_editText_valor)
        despesaCheckBox = findViewById(R.id.activity_formulario_checkBox_despesa)
        receitaCheckBox = findViewById(R.id.activity_formulario_checkBox_receita)
        botaoSalvar = findViewById(R.id.activity_formulario_button_salvar)
    }

    fun iniciarConexaoFireBase(){
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.activity_formulario_button_salvar -> {
                var descricao = descricaoEditText.text.toString()
                var valor = valorEditText.text.toString()
                var despesa = despesaCheckBox.isChecked
                var receita = receitaCheckBox.isChecked

                if(descricao.isEmpty()){
                    Toast.makeText(this,"Campo descricao em branco",Toast.LENGTH_SHORT).show()
                }

                if(valor.isEmpty()){
                    Toast.makeText(this,"Campo valor em branco",Toast.LENGTH_SHORT).show()
                }

                if(receita && despesa) {
                    Toast.makeText(this,"Não é possível escolher receita e despesa ao mesmo tempo.",Toast.LENGTH_SHORT).show()
                } else if(receita || despesa){
                    if(descricao.isNotEmpty() && valor.isNotEmpty()){
                        val id = firebaseDatabase.reference.child("usuarios/${firebaseAuth.uid}/transacoes").push().key
                        var transacao: Transacao? = null

                        if(despesa){
                            transacao = Transacao(id!!,descricao,valor,"Despesa")
                        } else if(receita){
                            transacao = Transacao(id!!,descricao,valor,"Receita")
                        }

                        val ref = firebaseDatabase.getReference("usuarios/${firebaseAuth.uid}/transacoes/${id}")
                        ref.setValue(transacao)
                        finish()
                    }
                }
            }
        }
    }
}