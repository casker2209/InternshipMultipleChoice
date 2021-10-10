# Internship Multiple Choice
Android Client for my multiple choice test app
### LoginActivity

Here, the user either fills in the TextView and then chooses to login, continue as guest or register a new account. 
If the user chooses to login, he is taken to the LoadingActivity to verify and request the account information 
If the user continues as guest, he is taken to the MenuActivity which contains navigation to other activities that implements the application’s functions
If the user chooses to register, he is taken to the SignupActivity to register a new account 

### LoadingActivity

The first screen that a user sees upon entering the application. Firstly, it checks whether the user information is stored in SharedPreferences, then either send the user to MenuActivity, or return the user to LoginActivity

If the user encounters this activity afterLoginActivity, then it would verify the login request, then in the case of success, save the user information to SharedPreferences, and send him to MenuActivity.Else, he is taken back to the LoginActivity


### MenuActivity

This is the activity that contains 4 CardViews that when clicked, will navigate the user to other activities: ExamListActivity,BankListActivity,ExamHistoryActivity and LoginActivity. 
In the case of navigation to LoginActivity, this is done through the SignOut method, which deletes the user information in SharedPreferences if he has logged in with an account.
If the user is a guest (doesn’t log in with an account),the method that navigates the User to ExamHistory will instead pop up an AlertDialog.

### RegisterActivity

This activity handles the registration process of a new account. Once the TextFields are filled and the Button in the layout is pushed, a POST request is sent to the API through the RetrofitClient. If the request is valid (the account information is accepted), a Toast will appear telling the user that the registration is successful,and he will be taken to LoginActivity.

### ExamListActivity and BankListActivity

These two activities are similar to each other in their nature: upon being entered, it will call a method that calls the API for resources (ExamInfo list and BankInfo list respectively) then apply it to the RecyclerView Adapter. In case of BankListActivity, each items have a Button that expands/collapses two LinearLayout that contains the Button with methods to either go to ExamActivity (which implements the Take a mock test use case) or BankActivity (which implements the Do a test bank use case)

### ExamActivity

The activity where the user takes the mock test. When a question’s choice is chosen, the CardView whose content corresponds to its position will turn white. A timer is created and shown to the user that when expired, will treat the test as completed. When the user completed it (early or not), he/she will be taken to the ExamFinishActivity which shows the exam result.The user can also cancel the test by pressing back.

### BankActivity

The activity where the user does the test bank. He can choose “complete” the current question, which will color the correct answer green and incorrectly chosen red. He can switch to another random question, or return to the MenuActivity

### ExamHistoryActivity and ExamFinishActivity

These two activities are quite similar to each other: ExamHistoryActivity represents a list of ExamResult that is represented in the same way as in ExamFinishActivity. The user can choose to expand or collapse sections that contain the item that represents question and its respectively correct answers in a unique color (or in the section that contains incorrect question, both the correct answers and incorrect chosen answers)
