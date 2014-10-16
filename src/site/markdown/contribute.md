How to contribute?
==================

This is a quick guide describing the essential steps to contribute.

Step 1: Connect to github
-------------------------

[Create](https://github.com/join)/[Use](https://github.com/login) your github account

Step 2: Fork the project
------------------------

Fork the [project](https://github.com/kbertet/java-lattices/fork)

Step 3: Clone your fork into your local machine
-----------------------------------------------

If you use ssh:

~~~
$ git clone git@github.com:{your_account}/java-lattices.git
~~~

If you use https:

~~~
$ git clone https://github.com/{your_account}/java-lattices.git
~~~

Step 4: Create a branch
-----------------------

We recommend to create several branches in order to implement several features or fix several bugs at the same time:

~~~
$ git checkout -b {new_branch_name}
~~~

Step 5: Take a cup of tea
-------------------------------------------

Start to code and when it's finished, modify the ChangeLog explaining the changes and commit all your work onto your local branch:

~~~
$ git commit -am 'My very usefull comment'
~~~

Step 6: Push you branch
-----------------------

Your code is now ready to be pushed onto your remote repository.

`origin` is the default name of your remote repository. You can get all your remote repositories with:

~~~
$ git remote -v
~~~

We recommand to rename your remote repository using your github account:

~~~
$ git remote rename origin {your_account}
~~~

~~~
$ git push {your_account} {new_branch_name}
~~~

Step 7: Submit your pull request
--------------------------------

Browse your remote repository, switch to your new branch and click on the pull request button. The button should redirect you to:

~~~
https://github.com/{your_account}/java-lattices/pull/new/{new_branch_name}
~~~

Step 8: Go to bed
-----------------

And wait for comments on your pull request from the developer team. Discuss with the team using github facilities and redo Step 5 to Step 6 until your code has been accepted.

Step 9: Delete the branch
-------------------------

The branch you was using for the new feature or bug fix is now obsolete.

You can delete it on your local machine using:

~~~
$ git checkout master
~~~

to switch to the master branch

~~~
$ git branch -D {new_branch_name}
~~~

to delete the branch

You can also delete the branch on your remote repository using:

~~~
$ git push {your_account} :{new_branch_name}
~~~

Step 10: Merge the official repository
--------------------------------------

If you want to add more features or to fix bugs, don't forget to merge the official repository in your master branch.

First you have to add a remote repository.

If you use ssh:

~~~
$ git remote add kbertet git@github.com:kbertet/java-lattices.git
~~~

If you use https:

~~~
$ git remote add kbertet https://github.com/kbertet/java-lattices.git
~~~

To view all your remote repositories:

~~~
$ git remote -v
~~~

You can now merge the up-to-date code in your master branch

~~~
$ git pull kbertet master
~~~

