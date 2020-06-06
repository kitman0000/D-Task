<template>
	<el-container>
		<el-main>
			<p>用户名:{{username}}</p>
			<p>密码:{{pwd}}</p>
			<p>昵称:{{nickname}}</p>
			<p>手机号:{{phone}}</p>
			<p>邮箱:{{email}}</p>
			<p>入职日期:{{onboardDate}}</p>
			<p>出生日期:{{birthday}}</p>
			<p>角色:{{role}}</p>
			<p>部门:{{department}}</p>
		</el-main>
	</el-container>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return{
				userID:1,
				pwd:"",
				role:"",
				department:"",
				onboardDate:"",
				username:"",
				nickname:"",
				phone:"",
				email:"",
				birthday:"",
				isShow: true,
			}
		},
		methods:{
			getUserDetail(){
				axios.get("/api/role/role",{
					headers:{
						token:localStorage.getItem("token"),
					}
				})
				.then(res => {
					var response = res.data;
					var roleObj = eval(response.data);
					console.log(roleObj);
					var roleID=new Array();
					var roleName=new Array();
					for(var i=0;i<roleObj.length;i++){
						roleID[i]=roleObj[i].roleID;
						roleName[i]=roleObj[i].roleName;
					}
					var roleTrans={};
					roleID.map((v,i)=>{
						roleTrans[roleID[i]] = roleName[i];
					})
					console.log(roleTrans);
					axios.get("/api/department/department",{
						headers:{
							token:localStorage.getItem("token"),
						}
					})
					.then(res => {
						var response = res.data;
						var departmentObj = eval(response.data);
						console.log(departmentObj);
						var departmentID=new Array();
						var departmentName=new Array();
						for(var i=0;i<departmentObj.length;i++){
							departmentID[i]=departmentObj[i].id;
							departmentName[i]=departmentObj[i].departmentName;
						}
						var departmentTrans={};
						departmentID.map((v,i)=>{
							departmentTrans[departmentID[i]] = departmentName[i];
						})
						console.log(departmentTrans);
						var params = new URLSearchParams();
						var userID =parseInt(localStorage.getItem("userID"));
						params.append("userID",userID);
						axios.get("/api/user/userDetail",{
							params:params,
							headers:{
								token:localStorage.getItem("token"),
							}
						})
						.then(res => {
							var response = res.data;
							var userObj = eval(response.data);
							console.log(userObj);
							this.username = userObj.username;
							this.pwd = userObj.pwd;
							this.nickname = userObj.nickName;
							this.email = userObj.email;
							this.role = roleTrans[userObj.roleID];
							this.phone = userObj.phone;
							this.birthday = userObj.birthday;
							this.department = departmentTrans[userObj.departmentID];
							this.onboardDate = userObj.onboardDate;
						});
					});
				});
			},
			showList(){
				this.isShow=!this.isShow;
			}
		},
		beforeMount:function() {
			this.getUserDetail();
		}
	}
</script>

<style>
	.theSelectedMenu {
		background-color: #24375E;
	}
	
	.otherSubMenu {
		background-color: #334E85;
	}
	
	.showListButton {
		position: fixed;
		left: 208px;
		top: 350px;
		width: 15px;
		height: 35px;
	}
	
	.hideListButton {
		position: fixed;
		left: 0px;
		top: 350px;
		width: 15px;
		height: 35px;
	}
</style>
