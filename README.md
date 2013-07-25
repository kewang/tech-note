---
published: false
---

tech-note
=========

初期使用[prose](prose.io)來線上編輯markdown，同時可以publish到github上面。後期可以進階到Travis+Octopress做auto deploy

# Git

## Convert subfolder into Git submodule
[git-dir2mod](https://github.com/kewangtw/git-dir2mod)

# Github

## 支援Markdown Syntax Highlight的語言
https://github.com/github/linguist/blob/master/lib/linguist/languages.yml

# Node.js

## 安裝Node.js
用NVM來管理Node.js的版本

`wget -qO- https://raw.github.com/creationix/nvm/master/install.sh | sh`

安裝Node.js

`nvm install 0.10`

使用Node.js

`nvm use 0.10`

在~/.zshrc加上下面指令，直接套用nvm
```bash
[[ -s /home/kewang/.nvm/nvm.sh ]] && . /home/kewang/.nvm/nvm.sh
nvm use 0.10
```

## npm安裝
npm安裝時如果加上-g即為全域模式，例如下面的方式

`npm install -g express`

## Node.js偵錯
安裝node-inspector幫Node.js偵錯，執行下面指令後開啟 http://localhost:8080/debug?port=5858

```bash
sudo npm install -g node-inspector

node --debug-brk=5858 yourfile.js
node-inspector &
```

## load不同js檔
可以使用[express-load](https://npmjs.org/package/express-load)這套npm，幫你有效管理不同路徑的routes/views/controllers。

## Jade語法
如果要在一個tag裡面包一個block的html語法，要在tag後面加上`.`，如下：

```html
p.
  <a href="foobar">click me</a>
  <a href="barfoo">click you</a>
```

如果要在任何一個view引入類似rails的partial頁面時，必須用下面的語法：

`include your-file-name`

## Express
預設輸出的html code都是unformat，如果要自動排版的話，要在app.js加入下面這行，一般而言會放在development的時候：

`app.locals.pretty = true;`

## Node.js書籍

[使用Express3.0实现<Node.js开发指南>中的微博系统](http://www.cnblogs.com/meteoric_cry/archive/2012/07/23/2604890.html)，這本書裡面講到的微博是用express 2.x開發的，由於現在express已經到3.x了，所以有蠻多部分都不適用，這個作者有整理了一些要注意的地方，值得一看。

[《Node.js开发指南》书中源码兼容express.js 3.x的版本](https://github.com/ericfish/Nodejs_Microblog)，這是相容於express 3.x的範例原始碼

## RESTful開發方式

* [Writing RESTful web services using Node.js](http://www.slideshare.net/FDConf/writing-restful-web-services-using-nodejs)，寫的非常完整，包含MongoDB的使用方式，值得一看。
* [影片](http://vimeo.com/51736205)

# Vim

## Vundle套件管理程式
[Vundle](https://github.com/gmarik/vundle)

安裝方式：

```bash
git clone https://github.com/gmarik/vundle.git ~/.vim/bundle/vundle
```

設定.vimrc
```vim
set nocompatible               " be iMproved
filetype off                   " required!

set rtp+=~/.vim/bundle/vundle/
call vundle#rc()

" let Vundle manage Vundle
" required! 
Bundle 'gmarik/vundle'

" My Bundles here:
"
" 從github來的bundle寫法
Bundle "name/repo"
" 從vim-scripts來的bundle寫法，http://vim-scripts.org/vim/scripts.html
Bundle 'repo-name'
" 從不是github來的git bundle寫法
Bundle 'git://yourgit.com/yourrepo.git'
" 從你電腦來的git bundle寫法
Bundle 'file:///home/kewang/path/to/plugin'

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

## YouCompleteMe
a code-completion engine for Vim

[github link](https://github.com/Valloric/YouCompleteMe)

### 先更新vim版本
```bash
sudo add-apt-repository ppa:nmi/vim-snapshots
sudo apt-get update
sudo apt-get upgrade
```

### 安裝YCM
在vim裡面輸入下面指令看是否有支援python，若為1就可以繼續安裝。

`:echo has('python')`

在.vimrc將YCM用vundle的方式import，然後輸入`:BundleInstall`就clone完成。

`Bundle 'Valloric/YouCompleteMe'`

安裝cmake及python-dev。

`sudo apt-get install cmake python-dev`

編譯YCM後就完成了。

```bash
cd ~
mkdir ycm_build
cd ycm_build

cmake -G "Unix Makefiles" . ~/.vim/bundle/YouCompleteMe/cpp

make ycm_core
```

# MongoDB
[Install MongoDB on Ubuntu](http://docs.mongodb.org/manual/tutorial/install-mongodb-on-ubuntu/)

## 安裝方式

```bash
sudo apt-key adv --keyserver keyserver.ubuntu.com --recv 7F0CEB10
echo 'deb http://downloads-distro.mongodb.org/repo/ubuntu-upstart dist 10gen' | sudo tee /etc/apt/sources.list.d/10gen.list
sudo apt-get update
sudo apt-get install mongodb-10gen
```

## 使用方式
可以利用`mongo`直接測試

# Push service

<table>
  <tr>
    <th>Vender</th>
    <th>Plan</th>
    <th>Price</th>
    <th>Features</th>
  </tr>
  
  <tr>
    <td rowspan="2">Parse</td>
    <td>Basic</td>
    <td>0</td>
    <td>reqs 1 million
pushes 1 million
burst 20reqs/s
</td>
  </tr>
  <tr>
    <td>Pro</td>
    <td>199</td>
    <td>reqs 15 million
pushes 5 million
burst 40reqs/s
adv. push target
push scheduling
</td>
  </tr>
  
  <tr>
    <td rowspan="2">Firebase</td>
    <td>Small</td>
    <td>99</td>
    <td>500 peak conns.
5GB storage
50GB transfer
</td>
  </tr>
  <tr>
    <td>Medium</td>
    <td>299</td>
    <td>2000 peak conns.
20GB storage
200GB transfer
</td>
  </tr>
  
  <tr>
    <td rowspan="2">Pusher</td>
    <td>Startup</td>
    <td>49</td>
    <td>500 conns
1 million/d
encryption
SSL Protection
</td>
  </tr>
  <tr>
    <td>Big Boy</td>
    <td>199</td>
    <td>5000 conns
10 million/d
encryption
SSL Protection
</td>
  </tr>
  
  <tr>
    <td rowspan="2">Pubnub</td>
    <td>Tier 2</td>
    <td>49</td>
    <td>500 peak conns
3000 active devices
</td>
  </tr>
  <tr>
    <td>Tier 2</td>
    <td>125</td>
    <td>3000 peak conns
10000 active devices
</td>
  </tr>
</table>

[關於Parse以及Firebase之間的比較](http://qr.ae/INTHl)，我覺得以公司現在的角度，或許比較適合用Firebase。因為Firebase可以將資料庫都dump出來，而Parse無法dump資料庫的資料。

## Parse

這是一個MBaaS (mobile backend as a service)，而且也已經被Facebook買下來了。

### Push實作方式

[Android push tutorial](https://www.parse.com/tutorials/android-push-notifications)

使用Pub/Sub的方式，userA發送訊息給userB時，可以用下面表示：

* userA使用下面方式發佈訊息給userB

```java
ParsePush push = new ParsePush();
push.setChannel("test msg");
push.setMessage("userB");
push.sendInBackground();
```

* 為了儲存userA發出的訊息，所以也要一起傳送給原來的backend。

```bash
curl -X POST -d '{"from" : "userA", "text" : "test msg", "to": "userB"}' https://chat.mitake.com.tw/message_list/userb.json
```

* userB使用下面方式訂閱訊息，而不用管是誰給他的訊息。

```java
PushService.subscribe(context, "userB", YourActivity.class);
```

or下面這種方式

```java
JSONObject data = new JSONObject("{\"action\": \"com.example.UPDATE_STATUS\",
                                   \"name\": \"Vaughn\",
                                   \"newsItem\": \"Man bites dog\""}));

ParsePush *push = new ParsePush();
push.setQuery(injuryReportsQuery);
push.setChannel("userB");
push.setData(data);
push.sendPushInBackground();
```

## Firebase

* [Wired的專訪](http://wired.tw/2012/05/23/firebase/index.html)
* [FAQ](https://www.firebase.com/docs/faq.html)，裡面講了很多我們考量的部分，值得一看。
* [How exactly are concurrent users determined for a Firebase app?](http://stackoverflow.com/questions/14307341/how-exactly-are-concurrent-users-determined-for-a-firebase-app)

### Push實作方式

使用Pub/Sub的方式，userA發送訊息給userB時，可以用下面REST API表示：

* userA使用 `curl -X POST -d '{"from" : "userA", "text" : "test msg", "to": "userB"}' https://chat.firebaseio.com/message_list/userb.json` 發佈訊息給userB
* 為了儲存userA發出的訊息，所以也要一起傳送給原來的backend `curl -X POST -d '{"from" : "userA", "text" : "test msg", "to": "userB"}' https://chat.mitake.com.tw/message_list/userb.json`。
* userB使用 `curl https://chat.firebaseio.com/message_list/userb.json` 訂閱訊息，而不用管是誰給他的訊息。

## Try NoSQL

* [Redis](http://try.redis.io/)
* [MongoDB](http://try.mongodb.org/)

## curl使用方式

* `-v`: verbose
* `-X`: method (PUT, POST, GET, DELETE...etc.)
* `-H`: header

### example

```bash
curl -v -X PUT -H "client_id: 2c1796d7-d929-42b3-a42d-18d2a4029e39" -H "access_token: 4ba56aad-f276-429e-ab8e-28cf778175af" http://localhost:8080/appserv/me/avator
```

## Heroku

### Add repo
* `heroku git:remote -a {heroku-app-name}` or
* `git remote add heroku {git-repo-url}`

### Git submodule的dependency解法
[Resolving Application Dependencies with Git Submodules](https://devcenter.heroku.com/articles/git-submodules)，如果上傳的app有用git submodule而且有綁帳號密碼的話，必須在git repo加上username:password，要不然heroku會無法clone下來。