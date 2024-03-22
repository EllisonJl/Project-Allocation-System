#!/usr/bin/env python
import subprocess
import os

required_version = [20, 0, 0]

# get the current installed version of node
node_version = subprocess.run(
    ["node", "--version"], capture_output=True, text=True
).stdout
node_version = node_version.strip()[1:].split(".")
node_version = list(map(lambda x: int(x), node_version))

# check if it's >= the required version
compatible = True
if node_version[0] < required_version[0]:
    compatible = False
elif node_version[0] == required_version[0] and node_version[1] < required_version[1]:
    compatible = False
elif (
    node_version[0] == required_version[0]
    and node_version[1] == required_version[1]
    and node_version[2] < required_version[2]
):
    compatible = False

if compatible:
    print("Your version of node is compatible.")
    exit(0)

# check machine architecture (this matches the teaching servers)
architecture = subprocess.run(["uname", "-mo"], capture_output=True, text=True).stdout.strip()
if architecture != "x86_64 GNU/Linux":
    print(
        "Not on an x86_64 Linux Machine. Please install Node.js >= "
        + ".".join(map(lambda x: str(x), required_version))
            + " manually."
    )
    exit(1)

# install compatible version of node.js
print("Installing Node.js v20.11.1 in the local directory\n")
subprocess.run(["wget", "https://nodejs.org/dist/v20.11.1/node-v20.11.1-linux-x64.tar.xz"])
subprocess.run(["tar", "xJf", "node-v20.11.1-linux-x64.tar.xz"])
os.remove("node-v20.11.1-linux-x64.tar.xz")
