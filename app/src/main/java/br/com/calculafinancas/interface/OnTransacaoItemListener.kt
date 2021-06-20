package br.com.calculafinancas.`interface`

import android.view.View

interface OnTransacaoItemListener {

    fun onTrasacaoItemClick(view: View, position: Int)

    fun onTransacaoItemLongClick(view: View, position: Int)
}