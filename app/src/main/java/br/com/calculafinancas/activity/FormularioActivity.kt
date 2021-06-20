package br.com.calculafinancas.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.View
import android.widget.*
import br.com.calculafinancas.R
import br.com.calculafinancas.model.Transacao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FormularioActivity : AppCompatActivity(), View.OnClickListener {

    private val NOME_BAR_TELA: String = "Cadastro"
    private lateinit var tituloTextView: TextView
    private lateinit var descricaoEditText: EditText
    private lateinit var valorEditText: EditText
    private lateinit var despesaCheckBox: CheckBox
    private lateinit var receitaCheckBox: CheckBox
    private lateinit var botaoSalvar: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var transacaoId:String
    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)
        setTitle(NOME_BAR_TELA)
        iniciarComponentesTela()
        botaoSalvar.setOnClickListener(this)
        iniciarConexaoFireBase()

        transacaoId = intent.getStringExtra("transacao") ?: ""

        if(transacaoId.isNotEmpty()){
            tituloTextView.text = "Editar Transação"

            var query = firebaseDatabase
                    .reference
                    .child("usuarios/${firebaseAuth.uid}/transacoes/${transacaoId}").orderByKey()

            query.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val transacao = snapshot.getValue(Transacao::class.java)!!

                    handler.post {
                        descricaoEditText.text = Editable.Factory.getInstance().newEditable(transacao.descricao)
                        valorEditText.text = Editable.Factory.getInstance().newEditable(transacao.valor)
                        if(transacao.tipo == "Receita") {
                            receitaCheckBox.isChecked = true
                        } else if(transacao.tipo == "Despesa") {
                            despesaCheckBox.isChecked = true
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    fun iniciarComponentesTela() {
        tituloTextView = findViewById(R.id.activity_formulario_textView_titulo)
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

    fun cadastrarTransacao(){

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
                } else if(receita == false && despesa == false){
                    Toast.makeText(this,"Receita e Despesa estão em branco, por favor, escolher um.",Toast.LENGTH_SHORT).show()
                } else if(receita || despesa){
                    // Cadastrar transação
                    if(descricao.isNotEmpty() && valor.isNotEmpty() && transacaoId.isEmpty()){
                        val id = firebaseDatabase.reference.child("usuarios/${firebaseAuth.uid}/transacoes").push().key
                        var transacao: Transacao? = null

                        if(despesa){
                            transacao = Transacao(id!!,descricao,valor,"Despesa")
                        } else if(receita){
                            transacao = Transacao(id!!,descricao,valor,"Receita")
                        }

                        val ref = firebaseDatabase
                                .getReference("usuarios/${firebaseAuth.uid}/transacoes/${id}")

                        ref.setValue(transacao)
                        finish()

                        // Atualizar transação
                    } else if(descricao.isNotEmpty() && valor.isNotEmpty() && transacaoId.isNotEmpty()){
                        var transacao: Transacao? = null

                        if(despesa){
                            transacao = Transacao(transacaoId,descricao,valor,"Despesa")
                        } else if(receita){
                            transacao = Transacao(transacaoId,descricao,valor,"Receita")
                        }

                        val ref = firebaseDatabase
                                .getReference("usuarios/${firebaseAuth.uid}/transacoes/${transacaoId}")
                        ref.setValue(transacao)
                        finish()

                    }
                }
            }
        }
    }
}