#!/bin/bash
# Author: randy
# Created Time : Mon 15 May 2017 04:00:45 PM CST
# File Name: add.sh
# Description: 常用的切换工作目录命令
#全局变量的定义，不要有空格
hexo_dir="/Users/randy/Documents/RandyBlog/"
user_dir="/Users/randy/"
# 跳转到制定dir
godir() {
	echo "0->hexo"
	echo "1->user"
	read -p "Please input the index of dir you want go:" index
	if [[ '0' = "$index" ]];then
		eval "cd $hexo_dir"
		eval "pwd"
	elif [[ '1' = "$index" ]]; then
		eval "cd $user_dir"
		eval "pwd"
	fi
}

function openbash() {
	echo "打开.bash_profile文件："
	eval "cd ~"
	eval "vim .bash_profile"
}

#发布文章
function pubblog() {
	eval "cd $hexo_dir"
	echo "查看git状态："
	eval "git status"
	eval "git pull --rebase"
	eval "git add ."
	echo "开始提交："
	eval "git commit -m '更新'"
	echo "开始推送："
	eval "git push"
	echo "推送完成"
	echo "开始发布文章"
	echo "切换至博客目录:"
	echo "执行clean操作:"
	eval "hexo clean"
	echo "执行生成操作:"
	eval "hexo g"
	echo "执行发布操作:"
	eval "hexo d"
	echo "发布成功"
}

# 启动kada应用
function kada() {
	echo "开始启动kada"
	eval "adb shell am start com.hhdd.kada/.main.ui.activity.LaunchActivity"
}

function openweb() {
	if [ -n "$1" ] 
	then
		echo "要打开的url：$1"
		eval "open -a '/Applications/Google Chrome.app' $1"
	else
		echo "要打开的url为空，只打开浏览器"
		eval "open -a '/Applications/Google Chrome.app'"
	fi
}



add(){
    local sum
    sum=$(( $1 + $2 ))
    echo "$1 + $2 = $sum"
}

