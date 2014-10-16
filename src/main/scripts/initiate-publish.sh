#!/bin/bash

if [ "$TRAVIS_REPO_SLUG" == "kbertet/java-lattices" ] && [ "$TRAVIS_JDK_VERSION" == "oraclejdk7" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then

	git config --global user.email "travis@travis-ci.org"
	git config --global user.name "travis-ci"
	current=`pwd`


	echo -e "Publishing doc...\n"

	echo -e "    Generating doc...\n"
	mvn site

	echo -e "    Getting gh-pages...\n"
	cd $HOME
	git clone --quiet https://${GH_TOKEN}@github.com/kbertet/java-lattices gh-pages > /dev/null
	cd gh-pages
	git checkout --orphan gh-pages
	git rm -rf .
	git push -fq origin :gh-pages > /dev/null
	cp $current/target/site/* .
	touch .nojekyll
	git add -f .
	git commit -m "    [ci skip] Latest doc on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
	git push -fq origin gh-pages > /dev/null
	echo -e "Published doc to gh-pages.\n"



fi
