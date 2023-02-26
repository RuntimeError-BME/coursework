# SW Project Coursework

Welcome to the RuntimeError team's coursework repository for Semester 22-23/2's Software Project Laboratory course (BMEVIIIAB02).

## Overview

This repository contains all the files and resources related to our school project. We are working trying to avoid technical debt and create a high-quality solution, so we shall document our architectural decisions, conventions and basic developer docs here. This README file provides an overview of the repository and instructions for getting started.

## Contents

The repository contains the following directories:

- `docs`: This directory contains documentation related to the project, such as design documents, requirements specifications, and user manuals.
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

For more detailed guide, check out this tutorial: [Cloning a repository](https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository).

### Create a New Branch
For more detailed guide, check out this tutorial: [Creating and managing branches](https://docs.github.com/en/desktop/contributing-and-collaborating-using-github-desktop/creating-and-managing-branches).



### Git Basics
4. Switch between branches, merge, push, pull, create a pull request, undo changes, and learn other basic Git knowledge by following this tutorial: [GitHub Git Handbook](https://guides.github.com/introduction/git-handbook/).

## Programming Conventions

**TBD**

## Further Reading

Useful cheatsheets & documentations:

- [Git cheatsheet](https://education.github.com/git-cheat-sheet-education.pdf)
- [JUnit 5 Guide](https://www.baeldung.com/junit-5)
- [Maven in 5 minutes](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
- [Java documentation](https://docs.oracle.com/en/java/)
- [UML basics](https://www.tutorialspoint.com/uml/uml_basics.htm)
- [GitHub documentation](https://docs.github.com/en)
- [Git documentation](https://git-scm.com/doc)
