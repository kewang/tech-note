tech-note
=========

[Convert subfolder into Git submodule](http://willandorla.com/will/2011/01/convert-folder-into-git-submodule)

# Node.js

## 安裝Node.js

```bash
sudo add-apt-repository ppa:chris-lea/node.js
sudo apt-get update
sudo apt-get install nodejs python-software-properties python g++ make
```

## NPM安裝
npm安裝時如果加上-g即為全域模式，例如下面的方式

`npm install -g express`

## Node.js偵錯
安裝node-inspector幫Node.js偵錯，執行下面指令後開啟 http://localhost:8080/debug?port=5858

```bash
sudo npm install -g node-inspector

node --debug-brk=5858 yourfile.js
node-inspector &
```

# Vim

## Vundle套件管理程式
[Vundle](https://github.com/gmarik/vundle)

安裝方式：

```bash
git clone https://github.com/gmarik/vundle.git ~/.vim/bundle/vundle
```

設定.vimrc
```
set nocompatible               " be iMproved
filetype off                   " required!

set rtp+=~/.vim/bundle/vundle/
call vundle#rc()

" let Vundle manage Vundle
" required! 
Bundle 'gmarik/vundle'

" My Bundles here:
"
" original repos on github
Bundle 'tpope/vim-fugitive'
Bundle 'Lokaltog/vim-easymotion'
Bundle 'rstacruz/sparkup', {'rtp': 'vim/'}
Bundle 'tpope/vim-rails.git'
" vim-scripts repos
Bundle 'L9'
Bundle 'FuzzyFinder'
" non github repos
Bundle 'git://git.wincent.com/command-t.git'
" git repos on your local machine (ie. when working on your own plugin)
Bundle 'file:///Users/gmarik/path/to/plugin'
" ...

filetype plugin indent on     " required!
"
" Brief help
" :BundleList          - list configured bundles
" :BundleInstall(!)    - install(update) bundles
" :BundleSearch(!) foo - search(or refresh cache first) for foo
" :BundleClean(!)      - confirm(or auto-approve) removal of unused bundles
"
" see :h vundle for more details or wiki for FAQ
" NOTE: comments after Bundle command are not allowed..
```