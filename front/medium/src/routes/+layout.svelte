<script>
	import '../app.css';
	import Header from "./header.svelte";

	const {children} = $props();
</script>


<div>
	<Header/>
	{@render children()}
</div>