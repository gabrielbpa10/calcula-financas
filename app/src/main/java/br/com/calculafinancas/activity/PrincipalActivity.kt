package br.com.calculafinancas.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import br.com.calculafinancas.R
import br.com.calculafinancas.`interface`.OnTransacaoItemListener
import br.com.calculafinancas.adapter.TransacaoAdapter
import br.com.calculafinancas.model.Transacao
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PrincipalActivity : AppCompatActivity(), OnTransacaoItemListener, View.OnClickListener {

    private lateinit var botaoAdd: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var transacaoAdapter: TransacaoAdapter
    private lateinit var handler: Handler
    private var transacoes: ArrayList<Transacao> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        iniciarConexaoFireBase()
        iniciarComponentesTela()
        handler = Handler(Looper.getMainLooper())

    }

    fun iniciarComponentesTela() {
        botaoAdd = findViewById(R.id.activity_principal_floatingActionButton)
        botaoAdd.setOnClickListener(this)
        recyclerView = findViewById(R.id.activity_principal_recycler_view)
    }

    fun iniciarConexaoFireBase(){
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val query = firebaseDatabase
                .reference
                .child("usuarios/${firebaseAuth.uid}/transacoes")
                .orderByKey()

        query.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                transacoes.clear()

                snapshot.children.forEach {
                    val transacao = it.getValue(Transacao::class.java)
                    transacoes.add(transacao!!)
                }

                transacaoAdapter = TransacaoAdapter(transacoes)
                transacaoAdapter.setOnTransacaoItemListener(this@PrincipalActivity)
                linearLayoutManager = LinearLayoutManager(applicationContext)

                handler.post {
                    recyclerView.apply {
                        adapter = transacaoAdapter
                        layoutManager = linearLayoutManager
                        //hasFixedSize()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    override fun onTrasacaoItemClick(view: View, position: Int) {
        val intentEdicao = Intent(this, FormularioActivity::class.java)
        intentEdicao.putExtra("transacao",transacoes[position].id)
        startActivity(intentEdicao)
    }

    override fun onTransacaoItemLongClick(view: View, position: Int) {
        val transacao = transacoes[position]

        val alerta = AlertDialog.Builder(this)
                .setTitle("Calcula Finanças")
                .setMessage("Você deseja excluir esta transação ${transacao.descricao} ?")
                .setPositiveButton("Sim") { dialog, _ ->
                    val ref =
                            firebaseDatabase
                            .reference.child("usuarios/${firebaseAuth.uid}/transacoes/${transacao.id}")

                    ref.removeValue().addOnCanceledListener {
                        transacoes.removeAt(position)
                        handler.post{
                            transacaoAdapter.notifyItemRemoved(position)
                        }
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Não") { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .create()
            alerta.show()
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.activity_principal_floatingActionButton ->{
                var intentFormulario = Intent(this,FormularioActivity::class.java)
                startActivity(intentFormulario)
            }
        }
    }


}