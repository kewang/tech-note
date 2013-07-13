tech-note
=========

[Convert subfolder into Git submodule](http://willandorla.com/will/2011/01/convert-folder-into-git-submodule)

# Node.js

npm安裝時如果加上-g即為全域模式，例如下面的方式

`npm install -g express`

安裝node-inspector幫Node.js偵錯，執行下面指令後開啟 http://localhost:8080/debug?port=5858

```bash
sudo npm install -g node-inspector

node --debug-brk=5858 yourfile.js
node-inspector &
```