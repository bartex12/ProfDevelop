package geekbrains.ru.translator.view.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import geekbrains.ru.model.data.DataModel
import geekbrains.ru.model.data.Meanings
import geekbrains.ru.translator.R
import kotlinx.android.synthetic.main.activity_main_recyclerview_item.view.*

class MainAdapter(private var onListItemClickListener: OnListItemClickListener) :
    RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    private var data: List<DataModel> = arrayListOf()

    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_main_recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.header_textview_recycler_item.text = data.text
                itemView.description_textview_recycler_item.text = convertMeaningsToString (data.meanings!!)

                itemView.setOnClickListener {  onListItemClickListener.onItemClick(data) }
            }
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }

    fun convertMeaningsToString(meanings: List<Meanings>): String {
        var meaningsSeparatedByComma = ""
        for ((index, meaning) in meanings.withIndex()) {
            meaningsSeparatedByComma += if (index + 1 != meanings.size) {
                String.format("%s%s", meaning.translation?.translation, ", ")
            } else {
                meaning.translation?.translation
            }
        }
        return meaningsSeparatedByComma
    }
}
