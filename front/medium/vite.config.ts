import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vitest/config';

export default defineConfig({
	plugins: [sveltekit()],
	test: {
		include: ['src/**/*.{test,spec}.{js,ts}']
	},
	server: {
		proxy: {
			"/md/": {
				// target: "http://localhost:8080",
				target: "http://172.17.0.1:8080",
				rewrite: (path) => path.replace(/^\/md/, ""),
			},

		},
	},
});
