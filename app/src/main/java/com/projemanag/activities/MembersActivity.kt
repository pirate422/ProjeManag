package com.projemanag.activities

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.projemanag.R
import com.projemanag.adapters.MemberListItemsAdapter
import com.projemanag.databinding.ActivityMembersBinding
import com.projemanag.databinding.DialogSearchMemberBinding
import com.projemanag.firebase.FirestoreClass
import com.projemanag.model.Board
import com.projemanag.model.User
import com.projemanag.utils.Constants

class MembersActivity : BaseActivity() {

    // ViewBinding instance for activity_members.xml
    private lateinit var binding: ActivityMembersBinding

    // TODO (Step 3: Create a global variable for Board Details.)
    // A global variable to hold board details received via intent
    private lateinit var mBoardDetails: Board

    private lateinit var mAssignedMembersList:ArrayList<User>

    // A global variable for notifying any changes done or not in the assigned members list.
    private var anyChangesDone: Boolean = false
    // END

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityMembersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO (Step 4: Get the Board Details through intent and assign it to the global variable.)
        if (intent.hasExtra(Constants.BOARD_DETAIL)) {
            mBoardDetails = intent.getParcelableExtra(Constants.BOARD_DETAIL)!!
        }

        // TODO (Step 6: Call the setup action bar function.)
        setupActionBar()

        // TODO (Step 5: Get the members list details from the database.)
        // START
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getAssignedMembersListDetails(
            this@MembersActivity,
            mBoardDetails.assignedTo
        )
        // END
    }

    // TODO (Step 5: Create a function to setup action bar.)
    /**
     * A function to set up the toolbar as the action bar with a back button.
     */
    private fun setupActionBar() {
        setSupportActionBar(binding.toolbarMembersActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        // Back press when navigation icon is clicked
        binding.toolbarMembersActivity.setNavigationOnClickListener { onBackPressed() }
    }
    /**
     * A function to setup assigned members list into recyclerview.
     */
    fun setupMembersList(list: ArrayList<User>) {

        mAssignedMembersList=list

        hideProgressDialog()

        binding.rvMembersList.layoutManager = LinearLayoutManager(this@MembersActivity)
        binding.rvMembersList.setHasFixedSize(true)

        val adapter = MemberListItemsAdapter(this@MembersActivity, list)
        binding.rvMembersList.adapter = adapter
    }
    // END
    // TODO (Step 3: Inflate the menu file for adding the member and also add the onOptionItemSelected function.)
    // START
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu to use in the action bar
        menuInflater.inflate(R.menu.menu_add_member, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.action_add_member -> {

                // TODO (Step 7: Call the dialogSearchMember function here.)
                // START
                dialogSearchMember()
                // END
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    // END
    // TODO (Step 5: Send the result to the base activity onBackPressed.)
    // START
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (anyChangesDone) {
            setResult(Activity.RESULT_OK)
        }
        super.onBackPressed()
    }
    // END
    private fun dialogSearchMember() {
        // Create a Dialog with this Activity's context
        val dialog = Dialog(this)

        // Inflate the layout using ViewBinding for the dialog
        val binding = DialogSearchMemberBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        // Handle "Add" button click
        binding.tvAdd.setOnClickListener {
            val email = binding.etEmailSearchMember.text.toString()

            if (email.isNotEmpty()) {
                dialog.dismiss()
                // You can call your member search logic here using `email`
                // TODO (Step 5: Get the member details from the database.)
                // START
                // Show the progress dialog.
                showProgressDialog(resources.getString(R.string.please_wait))
                FirestoreClass().getMemberDetails(this@MembersActivity, email)
                // END
            } else {
                Toast.makeText(
                    this@MembersActivity,
                    "Please enter member's email address.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Handle "Cancel" button click
        binding.tvCancel.setOnClickListener {
            dialog.dismiss()
        }

        // Show the dialog
        dialog.show()
    }
    fun memberDetails(user: User) {

        // TODO (Step 6: Here add the user id to the existing assigned members list of the board.)
        // START
        mBoardDetails.assignedTo.add(user.id)

        // TODO (Step 9: Finally assign the member to the board.)
        // START
        FirestoreClass().assignMemberToBoard(this@MembersActivity, mBoardDetails, user)
        // ENDss
    }
    // TODO (Step 7: Initialize the dialog for searching member from Database.)
    // START
    /**
     * A function to get the result of assigning the members.
     */
    fun memberAssignSuccess(user: User) {

        hideProgressDialog()

        mAssignedMembersList.add(user)

        anyChangesDone=true

        setupMembersList(mAssignedMembersList)
    }
    // END
}
