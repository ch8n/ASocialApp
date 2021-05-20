# Social App
Social feed app similar to linkedIn

# Requirements
1. Has a fixed set of pre-existing users, say 5 users.
2. Allows a user to log in using a phone number and some preset password. 
3. The Android system phone number picker is shown to easily pick the phone number.
4. Create a  test user - 1234567890
5. Each pre-existing user has a set of pre-existing posts (say 0-2 posts per user).
6. Each post has a pre-existing text and 0-5 pre-existing comments.
7. When a user logs in, he sees a homepage with two tabs: 1) My Feed 2) My Posts
8. In “My Feed”, he sees a vertically scrollable list of posts of everyone except his own posts
9. In “My Posts”, he sees a vertically scrollable list of his own posts and nothing else.
10. In both “My Feed” and “My Posts”, the posts that he sees are truncated versions. 
11. Each truncated post shows the name of the owner of the post, some avatar image of him,
12. first two lines of the post followed by three dots, and the numeric count of comments on that post
13. Clicking on a truncated post takes the user to a details page, where he sees the full post and all comments. 
14. Keep a logout option as well.
15. hardcode data in json files, 

NOTE: No features around creating a new post or adding a new comment etc. 
All posts and comments are pre-existing.

# Aim
1. Modular and decoupled code
2. Edge case handlings
3. Basic unit test case required only.
