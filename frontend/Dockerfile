# Stage 1: Build the React app
FROM node:18-alpine AS build

WORKDIR /app

COPY package.json .

# Install application dependencies.
RUN npm install --force

COPY . .

# Build the application.
RUN npm run build

# Stage 2: Serve the React app using Node.js
FROM node:18-alpine

WORKDIR /app

# Copy the built app from the previous stage.
COPY --from=build /app/build /app/build

# Expose the port (if needed).
EXPOSE 3000

# Install serve globally to serve static files.
RUN npm install -g serve --force

# Start the server to serve the built React app.
CMD ["serve", "-s", "build", "-l", "3000"]
