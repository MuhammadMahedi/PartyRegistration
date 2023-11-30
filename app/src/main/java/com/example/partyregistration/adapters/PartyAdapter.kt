package com.example.partyregistration.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.partyregistration.R
import com.example.partyregistration.data.Party

class PartyAdapter( private val context: Context,
                   private var list:List<Party> ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_party, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var party = list[position]
        if(holder is MyViewHolder){
            holder.tvPartyName.text = party.name
            holder.tvSecretaryName.text = party.secretary

            holder.delBtn.setOnClickListener {
                onClickListener?.onDeleteClick(party)
            }

            holder.itemView.setOnClickListener{
                if(onClickListener!=null){
                    onClickListener!!.onClick(position, party)
                }
            }

//            holder.delBtn.setOnClickListener{
//                onClickListener!!.onDeleteClick(party)
//            }
        }

    }

    class MyViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val tvPartyName = view.findViewById<TextView>(R.id.party_name)
        val tvSecretaryName = view.findViewById<TextView>(R.id.secretary_name)
        val delBtn= view.findViewById<ImageView>(R.id.delete_icon)
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener=onClickListener
    }

    interface OnClickListener{
        fun onClick(position:Int,model:Party)
        fun onDeleteClick(model:Party)

    }
}