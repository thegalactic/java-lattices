#!/bin/bash

if [ "$TRAVIS_REPO_SLUG" == "kbertet/java-lattices" ] && [ "$TRAVIS_JDK_VERSION" == "oraclejdk7" ] && [ "$TRAVIS_PULL_REQUEST" == "false" ] && [ "$TRAVIS_BRANCH" == "master" ]; then

  echo -e "Publishing javadoc, test results and test coverage...\n"

  cp -R build/doc $HOME/javadoc-latest
  cp -R build/reports/html $HOME/reports-latest
  cp -R build/coverage $HOME/coverage-latest

  cd $HOME
  git config --global user.email "travis@travis-ci.org"
  git config --global user.name "travis-ci"
  git clone --quiet --branch=gh-pages https://${GH_TOKEN}@github.com/kbertet/java-lattices gh-pages > /dev/null

  cd gh-pages
  git rm -rf ./api
  git rm -rf ./test
  git rm -rf ./coverage
  touch .nojekyll
  cp -Rf $HOME/javadoc-latest ./api
  cp -Rf $HOME/reports-latest ./test
  cp -Rf $HOME/coverage-latest ./coverage
  git add -f .
  git commit -m "Latest javadoc, test results and test coverage on successful travis build $TRAVIS_BUILD_NUMBER auto-pushed to gh-pages"
  git push -fq origin gh-pages > /dev/null

  echo -e "Published Javadoc, test results and test coverage to gh-pages.\n"
  
fi
