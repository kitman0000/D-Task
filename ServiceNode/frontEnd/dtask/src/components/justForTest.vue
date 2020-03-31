<template>
  <div>
	  <el-container>
	  
	    <el-aside width="200px" style="min-height: 700px;background-color: #24375E;">
	  	  <el-menu :default-openeds="[]" background-color="#24375E" text-color="#fff" active-color="#1D376B">
	  	  	<li v-for="(menu,menuKey) in menus">
	  	  		<el-submenu :index="menu.id">
	  	  			<template slot="title">{{menu.name}}</template>
	  	  				<div  v-for="(subMenu,subMenuKey) in menu.childMenuBoList">
	  	  						<el-menu-item :index="subMenu.id" >
	  	  								{{subMenu.name}}
	  	  						</el-menu-item>
	  	
	  	  				</div>
	  	  		</el-submenu>
	  	  	</li>
	  		</el-menu>
	    </el-aside>
	  </el-container>
  </div>
</template>

<script>

</script>
