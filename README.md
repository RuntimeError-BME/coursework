# SW Project Coursework

Welcome to the RuntimeError team's coursework repository for Semester 22-23/2's Software Project Laboratory course (BMEVIIIAB02).

## Overview

This repository contains all the files and resources related to our school project. We are working trying to avoid technical debt and create a high-quality solution, so we shall document our architectural decisions, conventions and basic developer docs here. This README file provides an overview of the repository and instructions for getting started.

## Contents

The repository contains the following directories:

- `docs`: TBD if needed
- `src`: This directory contains the source code for the project.
- `tests`: This directory contains test files for the project.
- `resources`: This directory contains resources such as images, icons, and other assets used in the project.

## Getting Started

To get started with the project, follow these steps:

### Adding an SSH Key to GitHub

In order to access the repository, you'll need to add your SSH key to your GitHub account. To do this, follow the steps below:

- Open Git Bash or Terminal on your local machine.
- Type `ssh-keygen -t ed25519 -C "your_email@example.com"` and press Enter. Make sure you enter the same E-Mail that you use for GitHub.
- It will prompt you to enter a file in which to save the key. Press Enter to accept the default file location (just press enter bro).
- You will then be prompted to enter a passphrase. You can either enter a passphrase or leave it blank.
- Your SSH key will be generated and saved to your default file location, typically in the `.ssh` directory.
- Use the command `cat ~/.ssh/id_ed25519.pub` to display your public SSH key.
- Copy the output to your clipboard.
- Log in to your GitHub account and go to `Settings > SSH and GPG keys`. Click `New SSH key`. Paste your public SSH key into the `Key` field. Give your key a descriptive title and click `Add SSH key`.
- You can now access the repository using your SSH key 😎
    
For a more detailed guide: [Adding a new SSH key to your GitHub account](https://docs.github.com/en/authentication/connecting-to-github-with-ssh/adding-a-new-ssh-key-to-your-github-account).

### Cloning the Repository

Type `cd "your-desired-directory" && git clone git@github.com:RuntimeError-BME/coursework.git`

For more detailed guide, check out this tutorial: [Cloning a repository](https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository).

### Git Basics

#### Creating Branches
1. From the command line, navigate to the root directory of your local repository.
2. Type `git checkout -b new_branch` and press Enter. Replace `new_branch` with the name of your new branch.
3. To make the new branch visible online, type `git push --set-upstream origin new_branch`
4. You are now working on the new branch. You can make changes to the code and commit them as usual.
5. To switch back to the main branch, type `git checkout main` and press Enter. Replace `main` with the name of your main branch.
For more detailed guide, check out this tutorial: [Creating and managing branches](https://docs.github.com/en/desktop/contributing-and-collaborating-using-github-desktop/creating-and-managing-branches).

#### Stashing Changes

Sometimes, you may need to temporarily set aside changes that you've made to your code, without committing them to your branch. This is where Git's stash command comes in handy. To stash your changes, follow these steps:

1. From the command line, navigate to the root directory of your local repository.
2. Type `git stash` and press Enter.
3. Your changes are now stashed, and you can switch to a different branch or perform other operations without your changes getting in the way.
4. To retrieve your stashed changes, type `git stash apply` and press Enter.
5. Your changes are now reapplied to your code. You can continue working on them and commit them to your branch when you're ready.

## Programming Conventions

**TBD**

## Further Reading

Useful cheatsheets & documentations:

- [Git cheatsheet](https://education.github.com/git-cheat-sheet-education.pdf)
- [GitHub Git Handbook](https://guides.github.com/introduction/git-handbook/).
- [JUnit 5 Guide](https://www.baeldung.com/junit-5)
- [Maven in 5 minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
- [Java documentation](https://docs.oracle.com/en/java/)
- [UML basics](https://www.tutorialspoint.com/uml/uml_basics.htm)
- [GitHub documentation](https://docs.github.com/en)
- [Git documentation](https://git-scm.com/doc)
