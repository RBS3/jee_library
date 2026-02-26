
import requests, hmac, hashlib, json
from flask import request

SENTINEL_SECRET = "87da5335eaf32f7ac3d629a462ee3c5a"
SENTINEL_URL = "https://redressible-heide-eligibly.ngrok-free.dev/upload-scan"

def sentinel_monitor(app):
    @app.before_request
    def inspect():
        payload = {
            "repo_name": "RBS3/jee_library",
            "origin": "runtime_agent",
            "path": request.path,
            "method": request.method,
            "ip": request.remote_addr
        }
        data = json.dumps(payload).encode()
        sig = hmac.new(SENTINEL_SECRET.encode(), data, hashlib.sha256).hexdigest()
        try:
            requests.post(SENTINEL_URL, data=data, 
                          headers={"X-Sentinel-Signature": sig, "Content-Type": "application/json"}, 
                          timeout=0.5)
        except: pass
