<template>
	<div>
		<h3>收件详情</h3>
		<p>发件人:{{sender}}</p>
		<p>标题: {{title}}</p>
		<p>内容: <br>{{content}}</p>
		<p>发件时间: {{sendTime}}</p>
		<p>是否紧急: {{isImportant}}</p>
		 <div  v-if='attachment.length !=0'>
		<label>附件：</label>
		<p v-for="item1 in attachment"><a :href="item1">{{item1}}</a></p>
		</div>
	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				title:'',
				content:'',
				sendTime:'',
				isImportant:'',
				attachment:[],
				sender:localStorage.getItem("sender"),
			}
		},
		methods:{
			getMailDetail(){
				axios.get('/api/mail/mailDetail', {
				    params: {
				      mailID:localStorage.getItem("chosenMail")
				    },headers:{
					"token":localStorage.getItem("token"),
				}
				  })
				  .then(res=>{
						var response=res.data.data;
						//this.sender = response.sender;
						this.receiver = response.receiver;
						this.title = response.title;
						this.content = response.content;
						this.sendTime = new Date(response.sendTime).toLocaleDateString().replace(/\//g, '-');
						if(response.important == true){
							this.isImportant = "是";
						}
						else
						{
							this.isImportant = "否";
						}
						if(response.attachment.length != 0){
							var a = response.attachment.split(',');
							for(var i =0 ;i<a.length;i++){
								this.attachment[i]=  window.location.protocol+"//"+window.location.host +"/file/"+a[i];
							}
						}
					})
				  .catch(err=> {
					  
				  });
			}
		},
		beforeMount:function(){
			this.getMailDetail();
		}
		}
</script>

<style>
	
</style>
