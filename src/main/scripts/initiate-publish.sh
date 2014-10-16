#!/bin/bash

if [ "$TRAVIS_REPO_SLUG" == "kbertet/java-lattices" ] && [ "$TRAVIS_JDK_VERSION" == "oraclejdk7" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then

	git config --global user.email "travis@travis-ci.org"
	git config --global user.name "travis-ci"
	current=`pwd`


	echo -e "Publishing doc...\n"

	echo -e "    Getting gh-pages...\n"
	cd $HOME
	git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/kbertet/java-lattices gh-pages > /dev/null

	echo -e "    Generating doc...\n"
	cd $current
	mvn site

	cd $HOME/gh-pages
	git rm -rf *
	touch .nojekyll
	cp -R $current/target/site/* .
	git add -f .
	git commit -m "    Latest doc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
	git push -fq origin :gh-pages
	git push -fq origin gh-pages > /dev/null
	echo -e "Published doc to gh-pages.\n"


	echo -e "Deploying...\n"

	echo -e "    Getting mvn-repo...\n"
	cd $HOME
	git clone --quiet --branch=mvn-repo https://${GH_TOKEN}@github.com/kbertet/java-lattices mvn-repo > /dev/null

	echo -e "    Deploying locally...\n"
	cd $current
	mkdir -fp target/mvn-repo/fr/kbertet/lattices
	test -f $HOME/mvn-repo/fr/kbertet/lattices/maven-metadata.xml && cp -f $HOME/mvn-repo/fr/kbertet/lattices/maven-metadata.* target/mvn-repo/fr/kbertet/lattices
	mvn deploy

	cd $HOME/mvn-repo
	find . -name `basename \`find $current/target/mvn-repo/ -name "*-SNAPSHOT"\`` -exec git rm -rf {} \;
	cp -R $current/target/mvn-deploy/* .
	git add -f .
	git commit -m "    Latest deploy on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to mvn-repo"
	git push -fq origin :mvn-repo
	git push -fq origin mvn-repo > /dev/null

	echo -e "Deployed to mvn-repo.\n"

fi
