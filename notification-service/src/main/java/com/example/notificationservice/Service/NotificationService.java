package com.example.notificationservice.Service;

import org.springframework.stereotype.Service;

import com.example.notificationservice.DTO.NotificationEvent;

@Service
public class NotificationService {
    private final EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void handlePaymentSuccess(NotificationEvent event) {
        String subject = "Thanh toán thành công - Đơn hàng #" + event.orderId();
        String body = """
                <html><body style="font-family: Arial, sans-serif; color: #333;">
                  <div style="max-width:600px; margin:auto; border:1px solid #e0e0e0; border-radius:8px; padding:24px;">
                    <h2 style="color:#27ae60;">✅ Thanh toán thành công</h2>
                    <p>Xin chào,</p>
                    <p>Đơn hàng <strong>#%s</strong> của bạn đã được thanh toán thành công.</p>
                    <table style="width:100%%; border-collapse:collapse; margin:16px 0;">
                      <tr style="background:#f9f9f9;">
                        <td style="padding:8px; border:1px solid #ddd;">Mã đơn hàng</td>
                        <td style="padding:8px; border:1px solid #ddd;"><strong>#%s</strong></td>
                      </tr>
                      <tr>
                        <td style="padding:8px; border:1px solid #ddd;">Số tiền</td>
                        <td style="padding:8px; border:1px solid #ddd;"><strong>%s VND</strong></td>
                      </tr>
                      <tr style="background:#f9f9f9;">
                        <td style="padding:8px; border:1px solid #ddd;">Trạng thái</td>
                        <td style="padding:8px; border:1px solid #ddd; color:#27ae60;"><strong>Thành công</strong></td>
                      </tr>
                    </table>
                    <p>Cảm ơn bạn đã mua hàng!</p>
                    <p style="color:#888; font-size:12px;">Email này được gửi tự động, vui lòng không trả lời.</p>
                  </div>
                </body></html>
                """.formatted(event.orderId(), event.orderId(), event.amount());

        emailService.sendEmail(event.userEmail(), subject, body);
    }

    public void handlePaymentFailure(NotificationEvent event) {
        String subject = "Thanh toán thất bại - Đơn hàng #" + event.orderId();
        String body = """
                <html><body style="font-family: Arial, sans-serif; color: #333;">
                  <div style="max-width:600px; margin:auto; border:1px solid #e0e0e0; border-radius:8px; padding:24px;">
                    <h2 style="color:#e74c3c;">❌ Thanh toán thất bại</h2>
                    <p>Xin chào,</p>
                    <p>Rất tiếc, thanh toán cho đơn hàng <strong>#%s</strong> của bạn đã thất bại.</p>
                    <table style="width:100%%; border-collapse:collapse; margin:16px 0;">
                      <tr style="background:#f9f9f9;">
                        <td style="padding:8px; border:1px solid #ddd;">Mã đơn hàng</td>
                        <td style="padding:8px; border:1px solid #ddd;"><strong>#%s</strong></td>
                      </tr>
                      <tr>
                        <td style="padding:8px; border:1px solid #ddd;">Số tiền</td>
                        <td style="padding:8px; border:1px solid #ddd;"><strong>%s VND</strong></td>
                      </tr>
                      <tr style="background:#f9f9f9;">
                        <td style="padding:8px; border:1px solid #ddd;">Trạng thái</td>
                        <td style="padding:8px; border:1px solid #ddd; color:#e74c3c;"><strong>Thất bại</strong></td>
                      </tr>
                    </table>
                    <p>Vui lòng thử lại hoặc liên hệ hỗ trợ để được giúp đỡ.</p>
                    <p style="color:#888; font-size:12px;">Email này được gửi tự động, vui lòng không trả lời.</p>
                  </div>
                </body></html>
                """.formatted(event.orderId(), event.orderId(), event.amount());

        emailService.sendEmail(event.userEmail(), subject, body);
    }
}
