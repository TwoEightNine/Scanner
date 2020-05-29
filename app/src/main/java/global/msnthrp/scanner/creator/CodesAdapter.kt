package global.msnthrp.scanner.creator

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import global.msnthrp.scanner.R
import global.msnthrp.scanner.base.BaseAdapter
import global.msnthrp.scanner.creator.model.AvailableCode
import kotlinx.android.synthetic.main.item_format.view.*

class CodesAdapter(
    context: Context,
    private val onClick: (AvailableCode) -> Unit
) : BaseAdapter<AvailableCode, CodesAdapter.CodeViewHolder>(context) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CodeViewHolder(inflater.inflate(R.layout.item_format, parent, false))

    override fun onBindViewHolder(holder: CodeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(code: AvailableCode) {
            with(itemView) {
                tvName.text = code.format.name
                ivCodeSample.setImageBitmap(code.bitmap)

                setOnClickListener { onClick(items[adapterPosition]) }
            }
        }
    }
}