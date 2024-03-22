#!/usr/bin/env bash

set -e

if [ -d "./node-v20.11.1-linux-x64" ]; then
    export PATH=./node-v20.11.1-linux-x64/bin/:$PATH
fi

# make sure we have a compatible node version.
./check-node.py

# check the PATH again, in case we just installed node
if [ -d "./node-v20.11.1-linux-x64" ]; then
    export PATH=./node-v20.11.1-linux-x64/bin/:$PATH
fi

npm install
npm run dev
