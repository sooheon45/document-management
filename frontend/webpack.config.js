// ... existing code ...
module.exports = {
    // ... existing code ...
    module: {
        rules: [
            // ... existing code ...
            {
                test: /\.js$/,
                exclude: /node_modules\/(?!quill)/, // quill 모듈을 Babel로 처리
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env'],
                        plugins: [
                            '@babel/plugin-proposal-class-properties',
                            '@babel/plugin-proposal-optional-chaining'
                        ]
                    }
                }
            }
        ]
    },
    // ... existing code ...
};