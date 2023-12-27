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
				target: "http://localhost:8080",
				rewrite: (path) => path.replace(/^\/md/, ""),
			},
		},
	},
});

// vite 설정은 개발 모드에서만 사용된다.
// 실제 build 시에는 제외 됨.