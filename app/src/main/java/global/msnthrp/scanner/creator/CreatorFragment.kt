package global.msnthrp.scanner.creator

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import global.msnthrp.scanner.R
import global.msnthrp.scanner.base.BaseFragment
import global.msnthrp.scanner.base.BaseViewModel
import global.msnthrp.scanner.creator.model.AvailableCode
import global.msnthrp.scanner.db.models.Code
import global.msnthrp.scanner.preview.CodePreviewActivity
import global.msnthrp.scanner.utils.setTopInsetPadding
import kotlinx.android.synthetic.main.fragment_creator.*

class CreatorFragment : BaseFragment() {

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(CreatorViewModel::class.java)
    }
    private val adapter by lazy {
        CodesAdapter(context ?: return@lazy null, ::onClick)
    }

    override fun getLayoutId() = R.layout.fragment_creator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etData.doOnTextChanged { text, _, _, _ ->
            invalidateCounter(text?.length ?: 0)
            adapter?.clear()
            tvCodesTitle.text = ""
        }
        invalidateCounter()
        btnCreate.setOnClickListener {
            viewModel.generateCodes(etData.text.toString())
        }
        ivClear.setOnClickListener {
            etData.setText("")
        }

        rvCodes.layoutManager = GridLayoutManager(context, 2)
        rvCodes.adapter = adapter

        etData.setTopInsetPadding()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.codes.observe(viewLifecycleOwner, Observer { codes ->
            tvCodesTitle.text = getString(R.string.create_codes_list_title, codes.size)
            adapter?.update(codes)
        })
    }

    override fun getDefaultViewModel() = viewModel

    private fun invalidateCounter(length: Int = 0) {
        tvCounter.text = getString(R.string.create_counter, length)
    }

    private fun onClick(availableCode: AvailableCode) {
        CodePreviewActivity.launch(
            context, Code(
                data = etData.text.toString(),
                codeType = availableCode.format.name,
                isScanned = false
            ),
            couldBeSaved = true
        )
    }

    companion object {

        fun newInstance() = CreatorFragment()
    }
}