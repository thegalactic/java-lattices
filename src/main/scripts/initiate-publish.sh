#!/bin/bash

if [ "$TRAVIS_REPO_SLUG" == "thegalactic/java-lattices" ] && [ "$TRAVIS_JDK_VERSION" == "oraclejdk8" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then

	git config --global user.email "travis@travis-ci.org"
	git config --global user.name "travis-ci"
	current=`pwd`

	echo -e "Publishing doc...\n"

	echo -e "    Generating doc...\n"
	mvn site

	echo -e "    Getting gh-pages...\n"
	cd $HOME
	git clone --quiet https://${GH_TOKEN}@github.com/thegalactic/java-lattices gh-pages > /dev/null
	cd gh-pages
	git checkout --orphan gh-pages
	git rm -rf .
	git push -fq origin :gh-pages > /dev/null
	cp -R $current/target/site/* .
	touch .nojekyll
	git add -f .
	git commit -m "    [ci skip] Latest doc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
	git push -fq origin gh-pages > /dev/null

	echo -e "Published doc to gh-pages.\n"


	echo -e "Deploying...\n"

	echo -e "    Getting mvn-repo...\n"
	cd $HOME
	git clone --quiet --branch=mvn-repo https://${GH_TOKEN}@github.com/thegalactic/java-lattices old-mvn-repo > /dev/null
	git clone --quiet https://${GH_TOKEN}@github.com/thegalactic/java-lattices mvn-repo > /dev/null
	cd mvn-repo
	git checkout --orphan mvn-repo
	git rm -rf .
	git push -fq origin :mvn-repo > /dev/null

	echo -e "    Deploying locally...\n"
	cd $current
	mkdir -p target/mvn-repo/org/thegalactic/lattices
	test -f $HOME/old-mvn-repo/org/thegalactic/lattices/maven-metadata.xml && cp -f $HOME/old-mvn-repo/org/thegalactic/lattices/maven-metadata.* target/mvn-repo/org/thegalactic/lattices
	mvn deploy

	cd $HOME/old-mvn-repo
	find . -name `basename \`find $current/target/mvn-repo/ -name "*-SNAPSHOT"\`` -exec git rm -rf {} \;
	cp -R $current/target/mvn-repo/* .

	cd $HOME/mvn-repo
	cp -Rf $HOME/old-mvn-repo/* .
	git add -f .
	git commit -m "    [ci skip] Latest deploy on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to mvn-repo"
	git push -f origin mvn-repo > /dev/null

	echo -e "Deployed to mvn-repo.\n"

fi
