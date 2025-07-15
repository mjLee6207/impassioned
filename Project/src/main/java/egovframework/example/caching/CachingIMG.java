/*
 * package egovframework.example.caching;
 * 
 * import java.io.IOException;
 * 
 * import javax.servlet.Filter; import javax.servlet.FilterChain; import
 * javax.servlet.FilterConfig; import javax.servlet.ServletException; import
 * javax.servlet.ServletRequest; import javax.servlet.ServletResponse; import
 * javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse;
 * 
 * public class CachingIMG implements Filter {
 * 
 * @Override public void doFilter(ServletRequest request, ServletResponse
 * response, FilterChain chain) throws IOException, ServletException {
 * 
 * HttpServletRequest req = (HttpServletRequest) request; HttpServletResponse
 * res = (HttpServletResponse) response; String uri = req.getRequestURI();
 * 
 * if (uri.contains("/file/download.do")) {
 * 
 * res.setHeader("Cache-Control", "public, max-age=604800"); // 캐시 유지 7일
 * res.setHeader("Expires", "Thu, 01 Jan 2030 00:00:00 GMT");
 * 
 * System.out.println("[캐시 적용됨]: " + uri); }
 * 
 * chain.doFilter(request, response);
 * 
 * System.out.println("[캐시필터 작동]: " + uri); }
 * 
 * // ++++++++++++++++++++++++++++++++++++++++++++++++++
 * 
 * @Override public void init(FilterConfig filterConfig) throws ServletException
 * {
 * 
 * }
 * 
 * @Override public void destroy() {
 * 
 * }
 * 
 * }
 */