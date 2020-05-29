package global.msnthrp.scanner.scanner

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import global.msnthrp.scanner.R
import global.msnthrp.scanner.base.BaseAdapter
import global.msnthrp.scanner.db.models.Code
import global.msnthrp.scanner.utils.toStringTime
import kotlinx.android.synthetic.main.item_code_mini.view.*

class CodesMiniAdapter(
    context: Context,
    private val onClick: (Code) -> Unit
) : BaseAdapter<Code, CodesMiniAdapter.CodeViewHolder>(context) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CodeViewHolder(inflater.inflate(R.layout.item_code_mini, parent, false))

    override fun onBindViewHolder(holder: CodeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(code: Code) {
            with(itemView) {
                tvData.text = code.data
                tvMeta.text = code.codeType
                tvDate.text = code.timeStamp.toStringTime(skipHours = true)

                val posFromEnd = itemCount - adapterPosition - 1
                tvData.maxLines = if (posFromEnd == 0) 4 else 1
                alpha = when (posFromEnd) {
                    0 -> 1f
                    1 -> .8f
                    2 -> .5f
                    3 -> .2f
                    else -> 0f
                }

                setOnClickListener { onClick(items[adapterPosition]) }
            }
        }
    }
}