package global.msnthrp.scanner.library

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import global.msnthrp.scanner.R
import global.msnthrp.scanner.base.BaseAdapter
import global.msnthrp.scanner.db.models.Code
import global.msnthrp.scanner.utils.toStringTime
import kotlinx.android.synthetic.main.item_code.view.*

class CodesAdapter(
    context: Context,
    private val onClick: (Code) -> Unit
) : BaseAdapter<Code, CodesAdapter.CodeViewHolder>(context) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CodeViewHolder(inflater.inflate(R.layout.item_code, parent, false))

    override fun onBindViewHolder(holder: CodeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CodeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(code: Code) {
            with(itemView) {
                tvData.text = code.data
                val metaRes = if (code.isScanned) {
                    R.string.code_meta_scanned
                } else {
                    R.string.code_meta_created
                }
                tvMeta.text = context.getString(metaRes, code.codeType)
                tvDate.text = code.timeStamp.toStringTime(skipHours = true)

                setOnClickListener { onClick(items[adapterPosition]) }
            }
        }
    }
}