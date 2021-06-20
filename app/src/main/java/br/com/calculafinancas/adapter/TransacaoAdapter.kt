package br.com.calculafinancas.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.calculafinancas.R
import br.com.calculafinancas.`interface`.OnTransacaoItemListener
import br.com.calculafinancas.model.Transacao

class TransacaoAdapter(
    val valores:List<Transacao>
): RecyclerView.Adapter<TransacaoAdapter.TransacaoViewHolder>() {

    private var listener: OnTransacaoItemListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransacaoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transacao_item,parent,false)
        return TransacaoViewHolder(view,listener)
    }

    override fun getItemCount(): Int {
        return valores.size
    }

    override fun onBindViewHolder(holder: TransacaoViewHolder, position: Int) {
        holder.itemDescricaoTransacao.text = valores[position].descricao
        holder.itemValorTransacao.text = valores[position].valor
        holder.itemTipoTransacao.text = valores[position].tipo

    }

    fun setOnTransacaoItemListener(listener: OnTransacaoItemListener){
        this.listener = listener
    }

    class TransacaoViewHolder(view: View, listener: OnTransacaoItemListener?): RecyclerView.ViewHolder(view){
        val itemDescricaoTransacao: TextView = view.findViewById(R.id.cardView_textView_descricao)
        val itemValorTransacao: TextView = view.findViewById(R.id.cardView_textView_valor)
        val itemTipoTransacao: TextView = view.findViewById(R.id.cardView_textView_tipo)

        init{
            view.setOnClickListener{
                listener?.onTrasacaoItemClick(it,adapterPosition)
            }

            view.setOnLongClickListener {
                listener?.onTransacaoItemLongClick(it,adapterPosition)
                true
            }
        }
    }
}