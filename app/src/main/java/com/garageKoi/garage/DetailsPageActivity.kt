package com.garageKoi.garage

import android.os.Bundle
import android.view.View
import com.garageKoi.garage.adapter.GarageTypeListAdapter
import com.garageKoi.garage.base.BaseActivity
import com.garageKoi.garage.databinding.ActivityDetailsPageBinding
import com.garageKoi.garage.dialog.ConfirmationDialog
import com.garageKoi.garage.dialog.ContactUsBottomSheetDialog
import com.garageKoi.garage.utils.Utils

class DetailsPageActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailsPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsPageBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.apply {
            // back
            imgBack.setOnClickListener {
                finish()
            }

            btnRequest.setOnClickListener {
                showConfirmationDialog("Your request has been sent\nsuccessfully.",false)
            }

            // cancel request
            btnCancel.setOnClickListener {
                showConfirmationDialog("Are you sure you want to cancel\nyour request?",true)
            }

            // contact
            btnContact.setOnClickListener {
                showContactBottomSheet()
            }




        }

        // setup garage type adapter
        setupGarageTypeAdapter()

    }

    private fun showContactBottomSheet() {
        ContactUsBottomSheetDialog(this,object :ContactUsBottomSheetDialog.OnClickListener{
            override fun onItemClick() {
                Utils.callPhone(this@DetailsPageActivity,"19225495517")
            }

        }).show()
    }

    private fun showConfirmationDialog(desc:String,showCancelBtn:Boolean) {
        ConfirmationDialog(this,desc,showCancelBtn,object :ConfirmationDialog.OnClickListener{
            override fun clickOkayBtn() {
                if (!showCancelBtn){
                    binding.layoutRequested.visibility = View.VISIBLE
                    binding.btnRequest.visibility = View.GONE
                }else{
                    binding.layoutRequested.visibility = View.GONE
                    binding.btnRequest.visibility = View.VISIBLE
                }
            }

            override fun clickCancelBtn() {}

        }).show()
    }

    private fun setupGarageTypeAdapter() {
        val items = listOf("Car Workshop","Truck Workshop","Bike Workshop")
        val listAdapter = GarageTypeListAdapter(this, items)
        binding.rvGarageType.adapter = listAdapter

    }
}