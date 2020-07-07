<template>
	<div>
	<el-table border :data="tableData" style="width: 100%">
		<el-table-column prop="date" label="日期" width="300">
			<template slot-scope="scope">
				<div @click="treeClick(scope.row,scope.$index)" style="cursor: pointer;">
					<template v-if="scope.row.children && scope.row.children.length > 0">
						<i class="el-icon-arrow-down" :style="'margin-left:'+(scope.row.level-1)*2+'em;'" v-if="scope.row.open"></i>
						<i class="el-icon-arrow-right" :style="'margin-left:'+(scope.row.level-1)*2+'em;'" v-else></i>
						<span >{{ scope.row.date }}</span>
					</template>
					<span v-else :style="'margin-left:'+(scope.row.level-1)*2+'em;'">{{ scope.row.date }}</span>
				</div>
			</template>
		</el-table-column>
		<el-table-column prop="name" label="姓名" width="180">
		</el-table-column>
		<el-table-column prop="address" label="地址">
		</el-table-column>
	</el-table>
</div>
</template>

<script>
	var util = {};
		util.treeTableXcode = function(data,xcode){
			xcode = xcode || "";
			for(var i=0;i<data.length;i++){
				var item = data[i];
				item.xcode = xcode + i;
				if(item.children && item.children.length > 0){
					util.treeTableXcode(item.children,item.xcode+"-");
				}
			}
		};
	 
		new Vue({
			el: "#app",
			data: function(){
				var tableData = [{
					id:"1",
					date: '2016-05-02',
					name: '王小虎',
					level:1,
					address: '上海市普陀区金沙江路 1518 弄',
					children:[{
						id:"11",
						date: '2016-05-02',
						name: '王小虎',
						level:2,
						address: '上海市普陀区金沙江路 1518 弄',
						children:[{
							id:"111",
							date: '2016-05-02',
							name: '王小虎',
							level:3,
							address: '上海市普陀区金沙江路 1518 弄'
						},{
							id:"112",
							level:3,
							date: '2016-05-02',
							name: '王小虎',
							address: '上海市普陀区金沙江路 1518 弄'
						},{
							id:"113",
							level:3,
							date: '2016-05-02',
							name: '王小虎',
							address: '上海市普陀区金沙江路 1518 弄'
						}]
					},{
						id:"12",
						level:2,
						date: '2016-05-02',
						name: '王小虎',
						address: '上海市普陀区金沙江路 1518 弄'
					},{
						id:"13",
						level:2,
						date: '2016-05-02',
						name: '王小虎',
						address: '上海市普陀区金沙江路 1518 弄'
					}]
				}, {
					id:"2",
					level:1,
					date: '2016-05-04',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1517 弄'
				}, {
					id:"3",
					level:1,
					date: '2016-05-01',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1519 弄'
				}, {
					id:"4",
					level:1,
					date: '2016-05-03',
					name: '王小虎',
					address: '上海市普陀区金沙江路 1516 弄'
				}];
				util.treeTableXcode(tableData);
				
				var data = {
					tableData: tableData
				}
				return data;
			} ,
			methods: {
				menuAdd: function() {
	 
				},
				treeClick:function(item,index){
					if(item.open){
						this.collapse(item,index);
					}else{
						this.expand(item,index);
					}
				},
				expand:function(item,index){
					if(!item.children){
						return index;
					}
					//展开
					for(var i=0;item.children && i<item.children.length;i++){
						var child = item.children[i];
						this.tableData.splice(++index,0,child);
						if(child.children && child.children.length > 0 && child.open){
							index = this.expand(child,index);
						}
					}
					item.open = true;
					return index;
				},
				collapse:function(item,index){
					if(!item.children){
						return index;
					}
					//收缩
					item.open = false;
					var len = 0;
					for(var i=index+1;i<this.tableData.length-1;i++){
						var xcode = this.tableData[i].xcode;
						if(xcode.startsWith(item.xcode+"-")){
							len ++;
						}else{
							break;
						}
					}
					this.tableData.splice(index+1,len);
				}
			}
		});

</script>

<style>
</style>
