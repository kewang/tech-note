#!/bin/bash

# $1 original repository
# $2 new submodule name
# $3 want to change dir
# $4 new submodule repository name

# Clone new repositories.
git clone --no-hardlinks $1 $2

# Filter out the files you want to keep and remove the others.
cd $2
git filter-branch --subdirectory-filter $3 HEAD -- --all
git reset --hard
git gc --aggressive
git prune
git remote rm origin

# Push the new repositories to the upstream server.
git remote add origin $4
git push -u origin master

# Add the new repository as submodules to the original repository
cd $1
git rm -r $3
git commit -m "Removing the folders that are now repositories"
git submodule add $4 $3
git submodule init
git submoduel update
git add .
git commit -m "Added in submodules for removed folders"
