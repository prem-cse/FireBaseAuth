FIREBASE AUTHENTICATION

 This Project Contains Sign In/Out facilities by Email And Password.
 
 1. authStateListener
 This function tells us the state of the user if the user is already signed in this
 function helps user to jump to other activity every time user fire the app
 because user dont want to authenticate every time if he was authenticated once.
 
 2. OnCompleteListener
  on Complete Listener act something like authStateListener till user is not signed in
  SO FOR EVERY QUERY WHEN USER IS NOT SIGNED IN WE USE OnCompleteListener
  ONCE SIGNED IN WE USE authStateListener
